import java.util.Iterator;
public class Deque<Item> implements Iterable<Item>
{
  
  private Item[] deq;
  private int N = 0; //capacity of the current queue
  private int first = 0;//pointer to the first element
  private int last = 0;//pointer to the end of the list
  
  public Deque()
  {
  // construct an empty deque
    deq = (Item[]) new Object [N];
  }
 public boolean isEmpty()
 {
  // is the deque empty?
  return (last-first) == 0;
 }
 public int size()
 {
  // return the number of items on the deque
  return last-first;
 }
 public void addFirst(Item item)
 {
  // insert the item at the front
  //first check how full the array is and if we need to resize it
   
   if (first == 0){resize(N*2,true);} //if the deque is full at the front
   size = size();
   first = N; //resets the first pointer 
   deq[--first] = item; 
   size = size + 1;
   last = first + size;// resets the last pointer
   N = N * 2;
   
 }
 public void addLast(Item item)
 {
  // insert the item at the end
   if (last == N){resize(N*2,false);}
   size = size();
   last = N; //resets the first pointer 
   deq[last++] = item; 
   size = size + 1;
   first = last + size;// resets the last pointer
   N = N * 2;
 }
 public Item removeFirst()
 {
  // delete and return the item at the front
  if (size() < N/4) {resize(N/2,true);}
  size = size();
  
  return deq[++first];
 }
 public Item removeLast()
 {
   if (size() < N/4){resize(N/2,false);}
  // delete and return the item at the end
  return deq[--last];
 }
 public Iterator<Item> iterator()
 {
  // return an iterator over items in order from front to end
   return new DeqIterator();
 }
 
 private class DeqIterator implements Iterator<Item>
 {
   private int i = last;
   
   public boolean hasNext() { return i>0;}
   public void remove() {/*not supported will throw an exception*/}
   public Item next() {return deq[--i];}
 }
 
 private void resize(int capacity, boolean fromFront)
 {
   Item[] copy = (Item[]) new Object[capacity];
   if (capacity > N)
   {
       for (int i = 0; i < N; i++)
       {
         if (fromFront) { copy[capacity-(i+1)] = deq[N - (i+1)]; }
         else { copy[i] = deq[i]; }
       }
   }
   else
   {
     if (fromFront)
     {
       for (int i = first; i <= last; i++)
       {
         copy[i-first] = deq[i];
       }
     }
     else
     {
       for (int i = last; i >= first; i--)
       {
         copy[capacity-(i-last+1)] = deq[i];
       }
     }
   }
   deq = copy;
 }

}