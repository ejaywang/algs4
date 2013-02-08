public class Percolation
{
  private boolean[][] sites;
  private int grid;
  private int beginNode;
  private int endNode;
  private WeightedQuickUnionUF QU;
  //private QuickFindUF QU;
  public Percolation(int N)
  {
    N = N+1;
    QU = new WeightedQuickUnionUF(N*N+2); //+2 for the top and bottom node
    //QU = new QuickFindUF(N*N+2);
    sites = new boolean[N][N]; // create N-by-N grid, with all sites blocked
    grid = N-1;
    beginNode = 0;
    endNode = N*N+1;
  }
  public void open(int i, int j)
  {
  // open site (row i, column j) if it is not already
    // check if out of bound:

    checkException(i, j); 
    if (!sites[j][i]) {
      sites[j][i] = true;
    }
    int index = indexCalc(j, i);
    //Connect the site with surrounding nodes
    //left:
    if (j != 1) {
      if (sites[j-1][i]) {
        QU.union(index, index-1);
      }
      }
    //right:
    if (j != grid) {
      if (sites[j+1][i]) {
        QU.union(index, index+1);
      }
    }
    //top:
    if (i != 1) {
      if (sites[j][i-1]) {
        QU.union(index, j+grid*(i-1));
      }
    }
    else {
      QU.union(beginNode, index); //connect with top node which we will just have as N^2
    }
    //bottom:
    
    if (i != grid) {
      if (sites[j][i+1]) {
        QU.union(index, j+grid*(i+1));
      }
    }
    else {
      QU.union(endNode, index); //connect with the end node which is the N^2+1 node
    }
  }
  
  public boolean isOpen(int i, int j)
  {

    checkException(i, j);
  // is site (row i, column j) open?
    return sites[j][i];
  }
  
  public boolean isFull(int i, int j)
  {

    checkException(i, j);
    System.out.println(QU.find(indexCalc(j,i)));
    System.out.println(QU.find(beginNode));
    System.out.println(QU.find(endNode));
  // is site (row i, column j) full?
    return (QU.connected(indexCalc(j,i), beginNode) );
  }
  

  
  public boolean percolates()
  {
    return QU.connected(beginNode, endNode); // does the system percolate?
  }
  
  private int indexCalc(int i, int j) {
    return i + grid*j;
  }
  private void checkException(int i, int j) { 
    if (i <= 0 || i > grid || j <= 0 || j > grid) {
      throw new IndexOutOfBoundsException("row index i out of bounds");
    }
  }
    
  public static void main(String[] args) {
    int grid_size = 3;
    Percolation perc = new Percolation(grid_size);
    //initialize the grid to something
//    for(int i=0;i<grid_size;i++){
//      for (int j=0;j<grid_size;j++){
//        System.out.println(perc.isOpen(i,j));
//      }
//    }

//  let's test if the openning of a site works. 
    perc.open(3,3);
    System.out.println(perc.isFull(3,3));
    perc.open(3,1);
    System.out.println(perc.isFull(3,3));
    perc.open(2,1);
    System.out.println(perc.isFull(3,3));
    perc.open(1,1);
    System.out.println(perc.percolates());
    System.out.println(perc.isFull(3,3));
  }
}