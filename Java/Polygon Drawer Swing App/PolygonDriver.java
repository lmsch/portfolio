/*
 * PolygonDriver - the driver class for the interactive polygon program
 * Author - Luke Schipper
 * Version - Jan. 19, 2017
 */

import javax.swing.JFrame;

public class PolygonDriver
{
    public static void main(String [] args)
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.getContentPane().add(new PolyPanel());
        
        frame.pack();
        frame.setVisible(true);
    }
}