
package com.atto.jawbreaker.resources;

import java.awt.Image;
import java.io.File;
import java.util.jar.JarFile;

import javax.imageio.ImageIO;

public class ImageBank
{
    public static final int SELECTED = -2;
    public static final int BLUE = 0;
    public static final int RED = 1;
    public static final int GREEN = 2;
    public static final int YELLOW = 3;
    public static final int GRAY = 4;
    public static final int ORANGE = 5;
    public static final int PURPLE = 6;
    
    private String path = "com/atto/jawbreaker/resources/";
    private String srcpath = "src/com/atto/jawbreaker/resources/";
    
    private Image blue;
    private Image red;
    private Image green;
    private Image yellow;
    private Image gray;
    private Image orange;
    private Image purple;
    private Image selected;
    /** Creates a new instance of ImageBank */
    public ImageBank()
    {
        try
        {
            selected = ImageIO.read(new File(srcpath + "selected.GIF"));
            blue     = ImageIO.read(new File(srcpath + "blue.GIF"));
            red      = ImageIO.read(new File(srcpath + "red.GIF"));
            green    = ImageIO.read(new File(srcpath + "green.GIF"));
            yellow   = ImageIO.read(new File(srcpath + "yellow.GIF"));
            gray     = ImageIO.read(new File(srcpath + "gray.GIF"));
            orange   = ImageIO.read(new File(srcpath + "orange.GIF"));
            purple   = ImageIO.read(new File(srcpath + "purple.GIF"));
        }
        catch(Exception e)
        {
            try
            {
                JarFile jf = new JarFile("jawbreaker.jar");
                selected = ImageIO.read(jf.getInputStream(jf.getEntry(path + "selected.GIF")));
                blue = ImageIO.read(jf.getInputStream(jf.getEntry(path + "blue.GIF")));
                red = ImageIO.read(jf.getInputStream(jf.getEntry(path + "red.GIF")));
                green = ImageIO.read(jf.getInputStream(jf.getEntry(path + "green.GIF")));
                yellow = ImageIO.read(jf.getInputStream(jf.getEntry(path + "yellow.GIF")));
                gray = ImageIO.read(jf.getInputStream(jf.getEntry(path + "gray.GIF")));
                orange = ImageIO.read(jf.getInputStream(jf.getEntry(path + "orange.GIF")));
                purple = ImageIO.read(jf.getInputStream(jf.getEntry(path + "purple.GIF")));
                jf.close();
            }
            catch (Exception ex)
            {
                System.out.println("Could not find images.");    
            }
        }
    }
    
    public Image getImage(int color)
    {
        switch(color)
        {
            case SELECTED:
                return selected;
            case BLUE:
                return blue;
            case RED:
                return red;
            case YELLOW:
                return yellow;
            case GREEN:
                return green;
            case GRAY:
                return gray;
            case ORANGE:
                return orange;
            case PURPLE:
                return purple;
        }
        return null;
    }
    
    
    
}
