import java.util.Arrays;
public class Fast {
   public static void main(String[] args)
   {
     int n = Integer.parseInt(args[0]); //# of points
     Point[] set = new Point[n];
     for (int i = 0; i < n; i++) 
     {
       int index = 2*i+1;
       int xPos = Integer.parseInt(args[index]);
       int yPos = Integer.parseInt(args[index+1]);
       set[i] = new Point(xPos, yPos);
     }
     Arrays.sort(set);
     for (int i = 0; i < n; i++)
     {
       int inARow = 0;
       Point[] slope_sorted = new Point[n];
       //make a copy of set
       for (int m = 0; m < n; m++)
       {
         slope_sorted[m] = set[m];
       }
       
       Point origin = set[i]; //The point we will be comparing to
       String points = origin.toString(); //this will be used later to construct the output
       Arrays.sort(slope_sorted,origin.SLOPE_ORDER); //sort the temp set by slope to the origin
       for (int j = 0; j < n-1; j++)
       {
         if (origin.slopeTo(slope_sorted[j]) == origin.slopeTo(slope_sorted[j+1])) 
         {
           inARow++;
           continue;
         }
         if (inARow >= 2)
         {
           for (int k = j - inARow; k < j + 1; k++)
           {
             
             if (origin.compareTo(slope_sorted[k]) != 0)
             {
               points = points + " -> " + slope_sorted[k].toString();
             }
           }
           System.out.println (points);
           points = origin.toString();
         }
         inARow = 0;
       }
     }
   }
}