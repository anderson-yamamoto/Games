package com.atto.jawbreaker;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.atto.jawbreaker.Engine;


public class MainWindow extends JFrame
{

    private static final long serialVersionUID = 1L;
    private Engine engine;
    private JLabel statusBar;
    private java.awt.Insets noBorder = new java.awt.Insets(0,0,0,0);
    public MainWindow(Engine engine2)
    {
        engine = engine2;
        setTitle("JawBreaker");
        setLayout(new java.awt.BorderLayout());
        //setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        statusBar = new JLabel("JAwBreaker - Atto");

        
        Box b = Box.createHorizontalBox();
        
        JButton bNew = new JButton("New");
        bNew.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                engine.showNewGameWindow();
            }
        });
        bNew.setMaximumSize(new Dimension(50,20));
        bNew.setMargin(noBorder);
        
        JButton bUndo = new JButton("Undo");
        bUndo.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                engine.undoLastAction();
            }
        });
        bUndo.setMaximumSize(new Dimension(50,20));
        bUndo.setMargin(noBorder);
        
        JButton bScores = new JButton("Scores");
        bScores.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
             System.exit(0);
            }
        });
        bScores.setMaximumSize(new Dimension(50,20));
        bScores.setMargin(noBorder);
        
        JButton bExit = new JButton("Exit");
        bExit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
             System.exit(0);
            }
        });
        bExit.setMaximumSize(new Dimension(50,20));
        bExit.setMargin(noBorder);
        
        b.add(bNew);
        b.add(bUndo);
        b.add(bScores);
        b.add(bExit);
        
        
        add(b, BorderLayout.NORTH);
        
        add(statusBar, BorderLayout.SOUTH);
    }
    
    public void updateScore(int score)
    {
        statusBar.setText("JAwBreaker - " + score);
    }
    
    public void endGame(int score)
    {
        JOptionPane.showMessageDialog(this, "Jogo finalizado.\nTotal de pontos: " + score);
    }
}
