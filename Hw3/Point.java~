/*************************************************************************
 * Name: Edward Wang
 * Email: ejaywang@outlook.com
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;
import java.util.Arrays;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SLOPE_ORDER();       // YOUR DEFINITION HERE
    
    
    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
      //check for infinity cases
      if (that.x == this.x) 
      {
        if (that.y > this.y){ return Double.POSITIVE_INFINITY; }
        else if (that.y < this.y) { return Double.NEGATIVE_INFINITY; }
        else { return 0.0; }
      }
      return ((double)that.y - (double)this.y)/((double)that.x-(double)this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) 
    {
        /* YOUR CODE HERE */
      if (this.y < that.y) { return -1; }
      else if (this.y == that.y)
      {
        if (this.x < that.x) { return -1; }
        if (this.x == that.x) {return 0; } 
      }
      return 1;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
    
    private class SLOPE_ORDER implements Comparator<Point>
    {
      public int compare (Point i, Point j)
      {
        double slope1 = slopeTo(i);
        double slope2 = slopeTo(j);
        if (slope1 < slope2) {return -1;}
        else if (slope1 > slope2) {return 1;}
        else {return 0;}
      }
    }
    // unit test
    public static void main(String[] args) 
    {
        /* YOUR CODE HERE */
      Point p0 = new Point(0, 0);
      Point[] set = new Point[10];
      for (int i = 0; i < 10; i++)
      {
        int xPos = StdRandom.uniform(10) - 5;
        int yPos = StdRandom.uniform(10) - 5;
        Point p = new Point(xPos,yPos);
        set[i] = p;
        System.out.print (p.toString());
      }
      Arrays.sort(set,p0.SLOPE_ORDER);
      Double[] slopes = new Double[10];
      for (int i = 0; i < 10; i++)
      {
        slopes[i] = p0.slopeTo(set[i]);
      }
      
      System.out.println("");
      System.out.println(Arrays.deepToString(slopes));
    }
}

