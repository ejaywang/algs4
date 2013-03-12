import java.util.HashMap;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Iterator;

public class Solver {
  private int next_node_solution;
  private int next_node_twin;
  private HashMap game_tree_solution;
  private HashMap game_tree_twin;
  private MinPQ move_to_process_solution;
  private MinPQ move_to_process_twin;
  private int not_solved;
  private int moves_taken;
  private SearchNode solution_board;
    public Solver(Board initial)            // find a solution to the initial board (using the A* algorithm)
    {
      
      /** There will be two data structures I will have to employ:
        * 1) The priority queue to enque all moves that has branched
        * 2) game tree consisting of move branches that could be potential
        * solution
        * There will be an inner class called SearchNode
        * Each instance of SearchNode will hold an instance of Board,
        * priority value as calculated by manhattan priority, move #,
        * a board ID, and a parent board ID
        * The board ID and parent board ID will create the edges in our
        * tree. 
        * */
      moves_taken = -1;
      solution_board = null;
      next_node_solution = 0;
      next_node_twin = 0;
      game_tree_solution = new HashMap();
      game_tree_twin = new HashMap();
      move_to_process_solution = new MinPQ<SearchNode>(); // initialize the move queue
      move_to_process_twin = new MinPQ<SearchNode>(); // initialize the move queue
      not_solved = 0;
      // initialize the first move
      SearchNode initial_node_solution = new SearchNode(initial, null, Integer.toString(next_node_solution), -1);
      SearchNode initial_node_twin = new SearchNode(initial.twin(), null, Integer.toString(next_node_twin), -1);
      move_to_process_solution.insert(initial_node_solution);
      move_to_process_twin.insert(initial_node_twin);
      next_node_solution = next_node_solution + 1;
      next_node_twin = next_node_twin +1;
      while (not_solved == 0)
      {
       checkNextPrioritySolution(); 
       checkNextPriorityTwin(); 
      }
    }
    
    private void checkNextPrioritySolution()
    {      
      SearchNode popped = (SearchNode) move_to_process_solution.delMin();
      game_tree_solution.put(popped.node_id,popped);
      //System.out.println(popped.manhattan);
      if (popped.manhattan == 0)
      {
        //System.out.println("done");
        moves_taken = popped.move_num;
        solution_board = popped;
        not_solved = 1;
      }
      enterNeighborNodesSolution(popped);
    }
    
    private void enterNeighborNodesSolution(SearchNode original_node)      
    {
      //System.out.println(original_node.search_board.toString());
      Iterable<Board> puzzle_neighbors = original_node.search_board.neighbors();
      for (Board neighbors : puzzle_neighbors)
      {
        boolean board_already_exists = false;
        SearchNode neighbor_node = new SearchNode(neighbors,original_node.node_id, Integer.toString(next_node_solution),original_node.move_num);
        String parent_id = neighbor_node.parent_id;
        while (parent_id != null)
        {
          SearchNode parent_node = (SearchNode) game_tree_solution.get(parent_id);
          if (neighbor_node.search_board.equals(parent_node.search_board))
          {
            board_already_exists = true;
            break;
          }
          parent_id = parent_node.parent_id;
        }
        if (!board_already_exists) 
        {
          move_to_process_solution.insert(neighbor_node);
          next_node_solution = next_node_solution + 1; 
        }
      } 
    }
    private void checkNextPriorityTwin()
    {      
      SearchNode popped = (SearchNode) move_to_process_twin.delMin();
      game_tree_twin.put(popped.node_id,popped);
      //System.out.println(popped.manhattan);
      if (popped.manhattan == 0)
      {
        //System.out.println("done");
        not_solved = 2;
      }
      enterNeighborNodesTwin(popped);
    }
    
    private void enterNeighborNodesTwin(SearchNode original_node)      
    {
      //System.out.println(original_node.search_board.toString());
      Iterable<Board> puzzle_neighbors = original_node.search_board.neighbors();
      for (Board neighbors : puzzle_neighbors)
      {
        boolean board_already_exists = false;
        SearchNode neighbor_node = new SearchNode(neighbors,original_node.node_id, Integer.toString(next_node_twin),original_node.move_num);
        String parent_id = neighbor_node.parent_id;
        while (parent_id != null)
        {
          SearchNode parent_node = (SearchNode) game_tree_twin.get(parent_id);
          if (neighbor_node.search_board.equals(parent_node.search_board))
          {
            board_already_exists = true;
            break;
          }
          parent_id = parent_node.parent_id;
        }
        if (!board_already_exists) 
        {
          move_to_process_twin.insert(neighbor_node);
          next_node_twin = next_node_twin + 1; 
        }
      } 
    }
    
    
    public boolean isSolvable()             // is the initial board solvable?
    {
      return solution_board != null;
    }
    
    public int moves()                      // min number of moves to solve initial board; -1 if no solution
    {
      return moves_taken;
    }
    
    
    public Iterable<Board> solution()       // sequence of boards in a shortest solution; null if no solution
    {
      ArrayDeque<Board> solution_path = new ArrayDeque<Board>();
      String move_id = solution_board.node_id;
      SearchNode current_node = solution_board;
      if (solution_board == null) return null;
      while (move_id != null)
      {
       current_node = (SearchNode) game_tree_solution.get(move_id);
       solution_path.push(current_node.search_board);
       move_id = current_node.parent_id;
      }
      return solution_path;
    }
    
      
    private class SearchNode implements Comparable<SearchNode>
    {
      public int move_num;
      public int manhattan;
      public int priority;
      public String node_id;
      public String parent_id;
      public Board search_board;
      
      public SearchNode(Board new_board, String parent, String id, int parent_move)
      {
        //initialize the node, given the board, the next node value as id, and
        //id to the parent node. 
        search_board = new_board;
        move_num = parent_move + 1;
        parent_id = parent;
        node_id = id;
        manhattan = search_board.manhattan();
        priority = manhattan + move_num;
                
      }
      public int compareTo(SearchNode that) 
      {
        if (this.priority < that.priority) { return -1; }
        else if (this.priority > that.priority) {return 1;}
        else return 0;
      }
    }
    
    
    public static void main(String[] args)  // solve a slider puzzle (given below)
    {
      int[][] arr0 = {{1, 8, 3},{0, 2, 4},{7,6,5}};
      int[][] arr1 = {{1, 2, 3},{4, 5, 6},{0,7,8}};
      int[][] arr2 = {{1, 2, 3, 4},{5, 6, 0, 8},{9, 10, 11, 12},{13, 14, 15, 7}};
      Board puzzle = new Board(arr0);
      Solver solve_puzzle = new Solver(puzzle);
      System.out.println(solve_puzzle.isSolvable());
      
      Iterable<Board> solution = solve_puzzle.solution();
      
      for (Board board : solution)
      {
        System.out.println(board.toString());  
      }
   
    }
}
