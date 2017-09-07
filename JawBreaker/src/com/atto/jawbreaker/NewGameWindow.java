package com.atto.jawbreaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.atto.jawbreaker.Engine;
import com.atto.jawbreaker.NewGameWindow;
import com.atto.jawbreaker.SpinnerChangeListener;

public class NewGameWindow extends JFrame
{

    private static final long serialVersionUID = 1L;
    private JSpinner horSpin;
    private JSpinner vertSpin;
    private JSpinner colorSpin;
    
    private Engine engine;
    public NewGameWindow()
    {
        setTitle("New Game");
        setLayout(null);
        setBounds(0,0,300,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        setResizable(false);
        
        JLabel horLabel = new JLabel("Horizontal Size");
        horLabel.setBounds(50, 30, 100, 20);
        JLabel vertLabel = new JLabel("Vertical Size");
        vertLabel.setBounds(50, 60, 100, 20);
        JLabel colorLabel = new JLabel("Number of colors");
        colorLabel.setBounds(50, 90, 100, 20);
        horSpin = new JSpinner();
        horSpin.addChangeListener(new SpinnerChangeListener(horSpin, 8, 20));
        horSpin.setValue(10);
        horSpin.setBounds(180, 30, 50, 20);
        
        vertSpin = new JSpinner();
        vertSpin.addChangeListener(new SpinnerChangeListener(vertSpin, 8, 20));
        vertSpin.setValue(10);
        vertSpin.setBounds(180, 60, 50, 20);
        
        colorSpin = new JSpinner();
        colorSpin.addChangeListener(new SpinnerChangeListener(colorSpin, 2, 7));
        colorSpin.setValue(5);
        colorSpin.setBounds(180, 90, 50, 20);
        
        JButton bStart = new JButton("Start");
        bStart.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                try{
                    if(engine == null)
                        engine = new Engine(getThis(),
                                   Integer.parseInt(horSpin.getValue().toString()), 
                                   Integer.parseInt(vertSpin.getValue().toString()), 
                                   Integer.parseInt(colorSpin.getValue().toString()));
                    else
                        engine.newGame(Integer.parseInt(horSpin.getValue().toString()), 
                                       Integer.parseInt(vertSpin.getValue().toString()), 
                                       Integer.parseInt(colorSpin.getValue().toString()));
                }catch(NumberFormatException nfe){}
            }
        });
        bStart.setBounds(40,120, 100, 20);
        
        JButton bExit = new JButton("Exit");
        bExit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
        bExit.setBounds(140,120, 100, 20);
        add(bStart);
        add(bExit);
        add(horSpin);
        add(vertSpin);
        add(colorSpin);
        add(horLabel);
        add(vertLabel);
        add(colorLabel);
        
        setVisible(true);
    }
    
    
    public NewGameWindow getThis()
    {
        return this;
    }
    
    public static void main(String[] args)
    {
        new NewGameWindow();
    }
}

class SpinnerChangeListener implements ChangeListener
{
    int minimum;
    int maximum;
    JSpinner source;
    public SpinnerChangeListener(JSpinner spin, int min, int max)
    {
        source = spin;
        minimum = min;
        maximum = max;
    }

    public void stateChanged(ChangeEvent e)
    {
        try
        {
            if (Integer.parseInt(source.getValue().toString()) > maximum)
            {
                source.setValue(maximum);
            }
            else if (Integer.parseInt(source.getValue().toString()) < minimum)
            {
                source.setValue(minimum);
            }    
        }
        catch (NumberFormatException nfe)
        {
            source.setValue(minimum);
        }
    }
}