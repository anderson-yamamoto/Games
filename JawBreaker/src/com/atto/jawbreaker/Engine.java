package com.atto.jawbreaker;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import com.atto.jawbreaker.Board;
import com.atto.jawbreaker.MainWindow;
import com.atto.jawbreaker.NewGameWindow;


public class Engine implements MouseListener
{
    public static int HOR_SIZE = 10;
    public static int VERT_SIZE = 10;
    public static int TOTAL_BLOCKS = 20;
    public static int BLOCK_COLORS = 5;
    private int[][] boardPieces;
    
    private NewGameWindow ngWindow;
    private MainWindow window;
    private Board field;
    
    private int selectedColor = -1;   //indicates the actual selection color...
                                      //means the block marked as -2 has this color
    private int numberSelected = 0;  //indicates how many pieces are selected, in order to calculate points
    private int totalScore = 0;
    
    private int numberOfColumns = HOR_SIZE; //is used when compacting blocks
    
    //variables for the UNDO 
    private int[][] lastBoardPieces; 
    private int lastSelectedColor = -1;
    private int lastNumberSelected = 0;
    private int lastTotalScore = 0;
    
    
    public Engine(NewGameWindow w, int hor, int vert, int colors)
    {
        ngWindow = w;
        HOR_SIZE = hor;
        numberOfColumns = HOR_SIZE;
        VERT_SIZE = vert;
        BLOCK_COLORS = colors;
        
        boardPieces = new int[HOR_SIZE][VERT_SIZE];
        generatePieces();
        
        window = new MainWindow(this);
        
        
        field = new Board(this);
        window.add(field);
        window.setBounds(0, 0, Board.PANEL_WIDTH + 5, Board.PANEL_HEIGHT + 71);
        window.setVisible(true);
        
        field.initGraphics();

        field.refresh(boardPieces);
    }    
    
    public void generatePieces()
    {
        Random rand = new Random(System.currentTimeMillis());
        for (int j = 0; j < VERT_SIZE; j++)
        {
            for (int i = 0; i < HOR_SIZE; i++)
            {
                boardPieces[i][j] = rand.nextInt(BLOCK_COLORS);
            }
        }
    }
    
    public void undoLastAction()
    {
        if(lastBoardPieces != null)
        {
            boardPieces = createClone(lastBoardPieces);
            numberSelected = lastNumberSelected;
            selectedColor = lastSelectedColor;
            totalScore = lastTotalScore;
            window.updateScore(totalScore);
            field.displayInfo((numberSelected * (numberSelected - 1)) + " points");
            field.refresh(boardPieces);
            lastBoardPieces = null;
        }
    
    }
    
    public void showNewGameWindow()
    {
        ngWindow.setVisible(true);
    }
    
    public void newGame(int hor, int vert, int colors)
    {
        HOR_SIZE = hor;
        VERT_SIZE = vert;
        BLOCK_COLORS = colors;
        boardPieces = new int[HOR_SIZE][VERT_SIZE];
        generatePieces();
        lastBoardPieces = null;
        numberSelected = 0;
        selectedColor = 0;
        totalScore = 0;
        window.updateScore(0);
        window.remove(field);
        
        field = new Board(this);
        window.setSize(0,0);
        window.add(field);
        field.initGraphics();
        field.refresh(boardPieces);
        window.setSize(Board.PANEL_WIDTH + 5, Board.PANEL_HEIGHT + 71);
    }
    
    // not used...
    public void mouseClicked(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
    
    //event handler
    public void mouseReleased(MouseEvent e)
    {
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            int x = e.getX() / 20, y = e.getY() / 20;
            if(x >= HOR_SIZE || y >= VERT_SIZE)
                return;
            
            if (boardPieces[x][y] == -2 )      //means a selected bloc has been clicked
            {
                if (numberSelected != 1)
                {
                    //setting up the history for the last move
                    lastBoardPieces = createClone(boardPieces); //to stop modifications in boardPieces 
                                                                //from happening in lastBoardPieces
                    lastNumberSelected = numberSelected;
                    lastSelectedColor = selectedColor;
                    lastTotalScore = totalScore;
                    dropdown();
                    totalScore += numberSelected * (numberSelected - 1);
                    compact(); //verifying if there are empty spaces between the columns
                    window.updateScore(totalScore);
                    field.refresh(boardPieces);
                    numberSelected = 0;
                    field.displayInfo("");
                    if (checkPairs())
                        window.endGame(totalScore);
                }
            }
            else if (boardPieces[x][y] != -1 ) //if a hollow spot has not been selected...
            {
                fillSelected();
                numberSelected = 0;
                selectedColor = boardPieces[x][y];
                check(x, y);  //beginning the recursive checking
                field.displayInfo((numberSelected * (numberSelected - 1)) + " points");
                field.refresh(boardPieces);
                //System.out.println(x + " " + y + " - valor: " + boardPieces[x][y]);  //debug
            }
            else
            {
                fillSelected();
                numberSelected = 0;
                field.displayInfo("");
                field.refresh(boardPieces);
            }
            
        }
    }
   
    
    /**
     *  Will mark the passed coordinates as being selected, and then will check the
     *  surrounding areas to verify if any of those points has the same color as this point.
     *  In that case, will recursively check those points too.
     *  @param x The x coordinate of the point to be cecked
     *  @param y The y coordinate of the point to be cecked
     */
    private void check(int x, int y)
    {
        try{
        if (boardPieces[x][y] != selectedColor)  //just checking...
        {
            selectedColor = boardPieces[x][y];
            System.out.println("This wasn´t supposed to happen...");
        }
        
        boardPieces[x][y] = -2;  //indicating that thi position is selected
        numberSelected++;
        if (x > 0)                //checking each of the sides
        {
            if (boardPieces[x - 1][y] == selectedColor)
                check(x - 1, y);
        }
        if (x < HOR_SIZE - 1)
        {
            
            if (boardPieces[x + 1][y] == selectedColor)
                check(x + 1, y);
            
        }
        if (y > 0)
        {
            if (boardPieces[x][y - 1] == selectedColor)
                check(x, y - 1);
        }
        if (y < VERT_SIZE - 1)
        {
            if (boardPieces[x][y + 1] == selectedColor)
                check(x, y + 1);
        }
        }catch(ArrayIndexOutOfBoundsException e){System.out.println( HOR_SIZE + " " + x);}
    }
    
    
    /**
     *  Will verify the existance of blocks marked as -2 and then will attempt to 
     *  fill those spaces with the blocks immediately up, "dropping" them down.
     */
    private void dropdown()
    {
        for (int j = VERT_SIZE - 1; j > -1; j--)
        {
            for (int i = 0; i < HOR_SIZE; i++)
            {
                if(boardPieces[i][j] == -2)
                {
                    for (int k = j; k > -2; k--)
                    {
                        if(k == -1)
                        {
                            for (int l = 0; l <= j; l++)
                            {
                                if (boardPieces[i][l] == -2)
                                {
                                    boardPieces[i][l] = -1;
                                }
                            }
                            break;
                        }
                        else if(boardPieces[i][k] == -1)
                        {
                            for (int l = k; l <= j; l++)
                            {
                                 if (boardPieces[i][l] == -2)
                                {
                                    boardPieces[i][l] = -1;
                                }
                            }
                            break;
                        }
                        else if(boardPieces[i][k] != -2)
                        {
                            boardPieces[i][j] = boardPieces[i][k] ;
                            boardPieces[i][k] = -2;
                            break;
                        }
                    }
                }
            }
        }
    }
    
    /**
     *  Turns the selected blocks back to their original appearance
     */
    private void fillSelected()
    {
        for (int j = 0; j < VERT_SIZE; j++)
        {
            for (int i = 0; i < HOR_SIZE; i++)
            {
                if (boardPieces[i][j] == -2)
                boardPieces[i][j] = selectedColor;
            }
        }
    }
    
    /**
     *  Should be called when blocks are moved.
     *  Verifies if there are empty columns and compacts them to the right.
     */
    private void compact()
    {
        for (int i = 0; i < numberOfColumns - 1; i++) 
        {                                      //this loop wont go to the last space to the left
            if (boardPieces[i][VERT_SIZE - 1] == -1)
            {
                for (int j = i; j < numberOfColumns - 1; j++)
                {                              //this one will just copy the arrays,
                                               //literally relocating everyone to the left
                    boardPieces[j] = boardPieces[j + 1];
                }
                
                numberOfColumns--;
                
                for (int j = 0; j < VERT_SIZE; j++)
                { //this loop is just for emptying the last column (first from the right, which is numberOfColumns - 1)
                    boardPieces[numberOfColumns][j] = -1;
                }
                
            }
        }
    }
    
    /**
     * Checks if there is any valid move still left. 
     * Should be called after every move to ensure the game has not ended
     * @return true if hasn´t found any pairs in the whole field, meaning the game is over. Otherwise, false
     */
    private boolean checkPairs()
    {
        int cont = 0, i = 0, direction = 1;
        for (int j = 0; j < VERT_SIZE; j++)
        {
            while (cont < HOR_SIZE - 1)
            {
                if(boardPieces[i][j] != -1)
                {
                    
                    if(boardPieces[i][j] == boardPieces[i + direction][j])
                        return false;
                    if (j != VERT_SIZE - 1)
                        if(boardPieces[i][j] == boardPieces[i][j + 1])
                            return false;
                }
                i += direction;
                cont++;
            }
            direction *= -1;
            cont = 0;
        }
        return true;
    
    }
    
    /**
     * Utility provided for creating clones of multidimensional arrays.
     * Apparently, int[][].clone() does not return a clone of the array properly, 
     * so this method had to be implemented in order to get efficient results.
     * @param source The multidimensional array of ints to be cloned
     * @return An array, identical to the source, but completely independent;
     *    in other words, a shallow copy of the source. 
     */
    private int[][] createClone(int[][] source)
    {
        int[][] temp = new int[source.length][];
        for (int i = 0; i < source.length; i++)
        {
            temp[i] = new int[source[i].length];
            for (int j = 0; j < source[i].length; j++)
            {
                temp[i][j] = source[i][j];
            }
        }
        return temp;
    }
}

