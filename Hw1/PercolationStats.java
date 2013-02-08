
public class PercolationStats {
  //called with the N and T. Where N is the size of the grid and T is the number
  //of trials that will be run. For each trial, we will calculate the number of 
  //sites that needed to be opened for percolation. 
  private int total_sites, trial_size, count;
  private double[] iterations;
  
  
  public PercolationStats(int N, int T) 
// perform T independent computational experiments on an N-by-N grid
  {
    checkException(N,T);
    total_sites = N*N;
    trial_size = T;
    iterations = new double[T];
    for (int i = 0; i < T; i++) { //perform T iterations
      Percolation perc = new Percolation(N); //initialize a percolation object
      count = 0; //initialize count
      while (!perc.percolates()) {
        //need to open some random site
        //int row = site_to_open[count] / (N) + 1;
        //int col = site_to_open[count] % (N-1) + 1;
        int row = StdRandom.uniform(1,N+1);
        int col = StdRandom.uniform(1,N+1);
        if (!perc.isOpen(col,row)) {
          count++; //increment count
        }
        perc.open(col, row);
      }
      iterations[i] = (double)count/(double)total_sites;
    }
    //System.out.println(Arrays.toString(iterations));
  }
  
   public double mean()                     // sample mean of percolation threshold
   {
     double sum = 0;
     for (int i = 0; i < trial_size; i++) {
       sum += iterations[i];
     }
     double mean = sum/(double)trial_size;
     return mean;
   }
   
   public double stddev()                   // sample standard deviation of percolation threshold
   {
     double sum = 0;
     double mean = mean();
     for (int i = 0; i < trial_size; i++) {
       sum += (iterations[i]-mean)*(iterations[i]-mean);
     }
     double stddev = Math.sqrt(sum/((double)trial_size-1));
     return stddev;
   }
   
   public double confidenceLo()             // returns lower bound of confidence interval
   {
     double mean = mean();
     double stddev = stddev();
     return mean-(1.96*stddev/Math.sqrt((double)trial_size));
     
   }
   public double confidenceHi()             // returns upper bound of confidence interval
   {
     double mean = mean();
     double stddev = stddev();
     return mean+(1.96*stddev/Math.sqrt((double)trial_size));
   }
   
   private void checkException(int N, int T){
     if (N <= 0 | T <= 0) {
       throw new java.lang.IllegalArgumentException("input needs to be positive values");
     }
   }
   
   public static void main(String[] args)   // test client, described below
   {
     int N = Integer.parseInt(args[0]);
     int T = Integer.parseInt(args[1]);
     PercolationStats PS = new PercolationStats(N,T);
     System.out.println(PS.mean());
     System.out.println(PS.stddev());
     System.out.println(PS.confidenceLo());
     System.out.println(PS.confidenceHi());
   }



}
