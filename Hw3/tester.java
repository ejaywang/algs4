public class tester {
  public static void main (String[] args){
    Point p = new Point(198, 268);
    Point q = new Point(498, 15);
    Point r = new Point(198, 430);
    double slope1 = p.slopeTo(q);
    double slope2 = p.slopeTo(r);
    if (slope1 < slope2) {System.out.println (-1);}
    else if (slope1 > slope2) {System.out.println (1);}
    else {System.out.println (0);}
  }
}