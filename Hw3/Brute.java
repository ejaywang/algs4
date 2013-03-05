import java.util.Arrays;
public class Brute {
  
   public static void main(String[] args)
   {
     //given an input file, check for any sets of 4 points that are collinear
     int n = Integer.parseInt(args[0]); //# of points
     Point[] set = new Point[n];
     for (int i = 0; i < n; i++) 
     {
       int index = 2*i+1;
       int xPos = Integer.parseInt(args[index]);
       int yPos = Integer.parseInt(args[index+1]);
       set[i] = new Point(xPos, yPos);
       //System.out.println (set[i].toString());
     }
     //sort the array:
     Arrays.sort(set);
     for (int i = 0; i < n - 3; i++)
     {
       for (int j = i + 1; j < n - 2; j++)
       {
         for (int k = j + 1; k < n -1; k++)
         {
           for (int l = k + 1; l < n; l++)
           {
             //here I compare the three slopes
             double slope1 = set[i].slopeTo(set[j]);
             double slope2 = set[i].slopeTo(set[k]);
             double slope3 = set[i].slopeTo(set[l]);
             if (slope1 == slope2 & slope2 == slope3 & slope1 == slope3)
             {
               System.out.println (set[i].toString() + " -> " + 
                                   set[j].toString() + " -> " + 
                                   set[k].toString() + " -> " + 
                                   set[l].toString());
             }
           }
         }
       }
     }
   }
}