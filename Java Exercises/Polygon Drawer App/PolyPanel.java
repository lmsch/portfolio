// PolyPanel - a custom panel class that provides the necessary info to the polylines class

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PolyPanel extends JPanel
{ 
    //objects
    private Point center, clicked;
    private Toolkit toolkit = Toolkit.getDefaultToolkit();    
    private Font myfont = new Font("Arial", Font.BOLD, 25);

    // ivars
    boolean clickOccured;
    private int numpoints;
    final private int BHEIGHT = (int)(toolkit.getScreenSize().getHeight()/1.5), BWIDTH =(int)(toolkit.getScreenSize().getWidth()/1.5), 
    MAXPOINTS = 30, MINPOINTS = 4;

    public PolyPanel()
    {
        addMouseListener(new ResizeListener()); // add listeners
        addMouseMotionListener(new ResizeListener());
        addKeyListener(new ArrowListener());
        
        numpoints = MINPOINTS; // set initial value of number of polygon points
        clickOccured = false;

        setFocusable(true);
        setPreferredSize(new Dimension(BWIDTH, BHEIGHT));
        setBackground(Color.white);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        center = new Point(getWidth()/2, getHeight()/2);  // recalculates center point before painting 
        if (clickOccured) // checks to see if click occured
        {
           Polylines.calculate(clicked, center, numpoints); // calls static method of polylines for calculating new polygon
           Polylines.draw(g); // polygon draws itself
        }
        g.setColor(Color.black);
        g.setFont(myfont);
        g.drawString("Points: " + numpoints, 10, getHeight() - 10); // paints label
    }

    private class ResizeListener implements MouseListener, MouseMotionListener
    {
        public void mouseDragged(MouseEvent event) 
        {
           mouseClicked(event);
        }

        public void mouseClicked (MouseEvent event) 
        {
            clickOccured = true;
            clicked = event.getPoint();
            repaint();
        }

        public void mouseMoved(MouseEvent event){}
        public void mouseReleased (MouseEvent event){}
        public void mouseEntered (MouseEvent event){}
        public void mouseExited (MouseEvent event){}
        public void mousePressed(MouseEvent event){}
    }

    private class ArrowListener extends KeyAdapter
    {
        public void keyPressed(KeyEvent event)
        { 
            switch(event.getKeyCode()) // switches the key that was pressed
            {
                case KeyEvent.VK_UP: // if up arrow
                if (numpoints < MAXPOINTS) // prevents going over maximum
                {
                    numpoints++; // increase number of points on polygon
                    repaint(); 
                }
                break;

                case KeyEvent.VK_DOWN: // if down arrow
                if (numpoints > MINPOINTS) // prevents under minimum
                {
                    numpoints--; // decrease number of points on polygon
                    repaint();
                }
                break;
            }
        }
    }
}