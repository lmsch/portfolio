// Polylines - class representing polygon, has static methods for calculations and drawing itself

import java.awt.*;

public class Polylines
{
    // ivars
    private static  Point [] lines;

    public static void draw(Graphics g)
    {
        g.setColor(Color.blue);
        for(int i = 0; i < lines.length - 1; i++) // draws a line from one points to every other point
        {
            for (int j = 0; j < lines.length; j++)
            {
                g.drawLine(lines[i].x, lines[i].y, lines[j].x, lines[j].y);
            }
        }
    }

    public static void calculate(Point clicked, Point center, int numpoints)
    {
        lines = new Point[numpoints];  // creates new array and stores clicked point
        lines[0] = clicked;
        // convert to cartesian
        double a = clicked.getX();
        double b = clicked.getY();
        a -= center.getX();
        b -= center.getY();
        b *= -1;
        // calculations
        double radius = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
        double theta = 2*Math.PI/numpoints;
        double alpha = Math.atan2(b, a);
        // generate new points
        for (int i = 1; i < lines.length; i++) // loops numpoints time
        {
            double c = radius*Math.cos(alpha + theta*i); // calculates news x and y coordinates, creating new point in array
            double d = radius*Math.sin(alpha + theta*i);
            d *= -1;
            c += center.getX();
            d += center.getY();
            lines[i] = new Point((int)c, (int)d);
        }
    }
}

