public class Percolation
{
  private boolean[][] sites;
  private int grid;
  private int beginNode;
  private int endNode;
  private WeightedQuickUnionUF QU;
  
  public Percolation(int N)
  {
    QU = new WeightedQuickUnionUF(N*N+2); //+2 for the top and bottom node
    sites = new boolean[N][N]; // create N-by-N grid, with all sites blocked
    grid = N;
    beginNode = N*N;
    endNode = N*N+1;
  }
  public void open(int i, int j)
  {
  // open site (row i, column j) if it is not already
    // check if out of bound:
    i = i - 1;
    j = j -1;
    checkException(i,j); 
    if (sites[i][j] == false) {
      sites[i][j] = true;
    }
    int index = indexCalc(i, j);
    //Connect the site with surrounding nodes
    //left:
    if (i != 0) {
      if (sites[i-1][j]) {
        QU.union(index, index-1);
      }
      }
    //right:
    if (i != grid-1) {
      if (sites[i+1][j]) {
        QU.union(index, index+1);
      }
    }
    //top:
    if (j != 0) {
      if (sites[i][j-1]) {
        QU.union (index, i+grid*(j-1));
      }
    }
    else {
      QU.union (index, beginNode); //connect with top node which we will just have as N^2
    }
    //bottom:
    
    if (j != grid-1) {
      if (sites[i][j+1]) {
        QU.union (index, i+grid*(j+1));
      }
    }
    else {
      QU.union (index, endNode); //connect with the end node which is the N^2+1 node
    }
  }
  
  public boolean isOpen(int i, int j)
  {
    i = i-1;
    j = j-1;
    checkException(i, j);
  // is site (row i, column j) open?
    return sites[i][j];
  }
  
  public boolean isFull(int i, int j)
  {
    i = i-1;
    j = j-1;
    checkException(i, j);
  // is site (row i, column j) full?
    return QU.connected(indexCalc(i,j), beginNode);
  }
  
  public boolean percolates()
  {
    return QU.connected(beginNode, endNode);// does the system percolate?
  }
  
  private int indexCalc(int i, int j){
    return i + grid*j;
  }
  private void checkException(int i, int j){

    if (i<0 || i >= grid || j<0 || j >= grid){
      throw new IndexOutOfBoundsException("row index i out of bounds");
    }
  }
  public static void main(String[] args) {
    int grid_size = 20;
    Percolation perc = new Percolation(grid_size);
    //initialize the grid to something
//    for(int i=0;i<grid_size;i++){
//      for (int j=0;j<grid_size;j++){
//        System.out.println(perc.isOpen(i,j));
//      }
//    }

//  let's test if the openning of a site works. 
    perc.open(1,6);
    System.out.println(perc.isFull(1, 6));
  }

}