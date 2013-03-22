import java.util.TreeSet;
import java.util.ArrayList;
import java.awt.Color;

public class KdTree 
{
  
  private Node root;
  private int size;
  private ArrayList<Node> nodes;
  private static final boolean VERT = true;
  private static final boolean HORIZ = false;
  private static final boolean LB = true;
  private static final boolean RT = false;
  private static class Node 
  {
    private Point2D p;// the point
    private RectHV rect; // the axis-aligned rectangle corresponding to this node
    private Node lb; //the left/bottom subtree
    private Node rt; //the right/top subtree
    private boolean orientation; //VERT or HORIZ
    
    public Node(Point2D point) //generate root node
    {
      this.p = point;
      this.orientation = VERT;
      this.rect = new RectHV(0.0,0.0,1.0,1.0);
    }
    
    public Node(Point2D point, Node parent)
    {
      this.p = point;
      this.orientation = !parent.orientation();
      parent.addChild(this);
      boolean side = parent.lbOrRt(point);
      double[] dimension = parent.rectDim();
      if (parent.orientation == VERT)
      {
        if (side == RT) dimension[0] = parent.x(); //change the xmin of the rectangle
        else dimension[2] = parent.x(); //change the xmax of the rectangle
      }
      else
      {
        if (side == RT) dimension[1] = parent.y(); //change the ymin of the rectangle
        else dimension[3] = parent.y(); //change the ymax of the rectangle
      }
      rect = new RectHV(dimension[0],dimension[1],dimension[2],dimension[3]);
      }


    public boolean lbOrRt(Point2D point)
    {
      double[] points_compare = pointsToCompare(point);
      if (points_compare[1] < points_compare[0]) return LB;
      else return RT;
    }
    
    public Node lbOrRtNode(Point2D point)
    {
      boolean side = lbOrRt(point);
      if (side == LB) return this.lb;
      else return this.rt;
    }
    
    public double[] pointsToCompare(Point2D point)
    {
      double[] points = new double[2];
      if (this.orientation == VERT) 
      {
        points[0] = this.p.x();
        points[1] = point.x();
      }
      else 
      {
        points[0] = this.p.y();
        points[1] = point.y();
      } 
      
      return points;
    }
    
    public void addChild (Node child) 
    {
      if (this.orientation == VERT)
      {
        if (child.p.x() < this.p.x()) this.lb = child;
        else this.rt = child;
      }
      else
      {
        if (child.p.y() < this.p.y()) this.lb = child;
        else this.rt = child;
      }
    }
    public boolean orientation()
    {
      return this.orientation;
    }
    
    public boolean equals(Point2D point)
    {
      return this.p.equals(point);
    }
    public double[] rectDim()
    {
      double[] dimension = {this.rect.xmin(),this.rect.ymin(),this.rect.xmax(),this.rect.ymax()};
      return dimension;
    }
    public Point2D point() {return this.p;}
    
    public double x() {return this.p.x();}
    public double y() {return this.p.y();}
  }
  
  public KdTree()                               // construct an empty set of points
  {
    this.size = 0;
    this.nodes = new ArrayList<Node>();
  }

  public boolean isEmpty()
  {
    return size == 0;
  }
  
  public int size()
  {
    return size;
  }
      
  public void insert(Point2D p)                   // add the point p to the set (if it is not already in the set)
  {
    
    if (this.isEmpty())
    {
      root = new Node(p);
      nodes.add(root);
    }
    else
    {
      this.insert(p,root); // traverse the tree and find the parent to insert the Node to. 
    }
    this.size++;
  }
  
  private void insert(Point2D p, Node parent)
  {
    Node next_node = parent.lbOrRtNode(p);
    if (next_node == null) 
    {
      Node new_node = new Node(p,parent);// once lb or rt is null, add to the corresponding node and generate the new Node. 
      nodes.add(new_node);
    }
    else this.insert(p,next_node);// recursive function that makes a compare with the parent node's lb and rt to determine where to traverse to next
  }
  

  public boolean contains(Point2D p)              // does the set contain the point p?
  {
    if (this.isEmpty()) return false;
    else return this.contains(p,root);//traverse tree recursively and compare. 
    
  }
  
  private boolean contains(Point2D p, Node parent)
  {
    if (parent.equals(p)) return true;
    else
    {
      Node next_node = parent.lbOrRtNode(p);
      if (next_node == null) return false;
      else return this.contains(p,next_node);
    }
  }
  
  
  public void draw()                              // draw all of the points to standard draw
  {
    for (Node node : nodes)
    {
      double[] rect_dimension = node.rectDim();
      if (node.orientation == VERT) 
      {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.line(node.x(),rect_dimension[1],node.x(),rect_dimension[3]);
      }
      else 
      {
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.line(rect_dimension[0],node.y(),rect_dimension[2],node.y());
      }
    }
  }
  
  /*
  public Iterable<Point2D> range(RectHV rect)     // all points in the set that are inside the rectangle
  {
    ArrayList<Point2D> contained_points = new ArrayList<Point2D>();
    for (Point2D point : point_set)
    {
     if (point.x() > rect.xmin() & point.x() < rect.xmax() & point.y() > rect.ymin() & point.y() < rect.ymax())
     {
       contained_points.add(point);
     }
    }
    return contained_points;
  }
  public Point2D nearest(Point2D p)               // a nearest neighbor in the set to p; null if set is empty
  {
    Point2D nearest_point = point_set.first(); //initialize this with the first point
    if (isEmpty()) return null;
    else
    {
      for (Point2D point : point_set)
      {
        if (p.distanceTo(point) < p.distanceTo(nearest_point)) nearest_point = point;
      }
      return nearest_point;
    }
  }
  */
  public static void main(String[] args)
  {
    KdTree point_set = new KdTree();

    for (int i = 0; i < args.length; i = i + 2)
    {
      Point2D point = new Point2D(Double.parseDouble(args[i]),Double.parseDouble(args[i+1]));
      point_set.insert(point);
    }
    
    Point2D point = new Point2D(0.021,0.1);
    point_set.draw();
    
  }
}
