

package com.atto.jawbreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import com.atto.jawbreaker.resources.ImageBank;

import com.atto.jawbreaker.Engine;

public class Board extends JPanel
{
    private static final long serialVersionUID = 1L;
    public static int PANEL_WIDTH = 400;
    public static int PANEL_HEIGHT = 400;
    
    private Engine engine;

    private ImageBank images;
    
    private Image offscreen;
    private Graphics buffer;
    private int[][] diagram;
    /**
     * Creates a new instance of Board
     */
    public Board(Engine eng)
    {
        engine = eng;
        PANEL_WIDTH = Engine.HOR_SIZE * 21;
        PANEL_HEIGHT = Engine.VERT_SIZE * 21;
        
        setBackground(Color.WHITE);
        addMouseListener(engine);
        
        images = new ImageBank();
    }
    
    public void initGraphics()
    {
        offscreen = createImage(PANEL_WIDTH, PANEL_HEIGHT);
        buffer = offscreen.getGraphics();
    }
    
    public void refresh(int[][] pieces)
    {
        diagram = pieces;
        repaint();
    }
    
    
    public void displayInfo(String text)
    {
        this.setToolTipText(text);
    }
    
    public void update(Graphics g)
    {
        super.update(g);
        try
        {
            paint(g);
        }catch(Exception e)
        {
            System.out.println("Exception: \n" + e.getMessage());
        }
    }
    
    public void paint(Graphics g)
    {
        super.paint(g);
        if(buffer == null) return;        
        buffer.setColor(Color.WHITE);
        buffer.clearRect(0,0,PANEL_WIDTH,PANEL_HEIGHT);
        
        buffer.setFont(new Font("Arial", Font.BOLD, 20));
        if (true)              //TODO
        {
            for (int i = 0; i < diagram.length; i++)
            {
                for (int j = 0; j < diagram[i].length; j++)
                {
                    buffer.drawImage(images.getImage(diagram[i][j]), i * 20, j * 20, null);
                }
            }
        }
        g.drawImage(offscreen,0,0,this);
    }
}
