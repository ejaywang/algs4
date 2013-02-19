public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] RQ;
  private int N = 0; //capacity of the current queue 
  private int first = 0;
  private int last = 0;
  public RandomizedQueue()           // construct an empty randomized queue
   {
     RQ = (Item[]) new Object [N];
   }
   public boolean isEmpty()           // is the queue empty?
   {
     return (last - first) == 0;
   }
   public int size()                  // return the number of items on the queue
   {
     return last - first
   }
   public void enqueue(Item item)     // add the item
   {
     // this will just place the item at the end
     // check if last = N, if so, resize the queue to double the size
     if (last == N) {resize(N*2);}
     RQ[last++] = item;
   }
   public Item dequeue()              // delete and return a random item
   {
     //this will delete a random item and move the last item to the spot
     //check if last < N/4, if so, resize the queue to half the size
     if (last == N/4){resize(N/2);}
     remove = StdRandom.uniform(last+1);
     temp = RQ[remove];
     RQ[remove] = RQ[last--]; //replaces the removed item with last and decrement last.
     //if remove == last, then last will be "removed" simply because last decrements
     return temp;
    
   }
   public Item sample()               // return (but do not delete) a random item
   {
     select = StdRandom.uniform(last+1);
     return RQ[select];
   }
   public Iterator<Item> iterator()   // return an independent iterator over items in random order
   {
     return new RQIterator();
   }
    private class DeqIterator implements Iterator<Item>
    {
      private int i = last;
      
      public boolean hasNext() { return i>0;}
      public void remove() {/*not supported will throw an exception*/}
      public Item next() {return deq[--i];}
    }
 
    private void resize(int capacity)
    {
      Item[] copy = (Item[]) new Object[capacity];
      for (int i = 0; i < capacity; i++){
        copy[i] = RQ[i];
      }
      
      RQ = copy;
    }
}