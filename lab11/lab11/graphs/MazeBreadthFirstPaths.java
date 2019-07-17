package lab11.graphs;
import edu.princeton.cs.algs4.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int sourceAdd;
    private int targetAdd;
    private boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        sourceAdd = maze.xyTo1D(sourceX, sourceY);
        targetAdd = maze.xyTo1D(targetX, targetY);
        edgeTo[sourceAdd] = sourceAdd;
        distTo[sourceAdd] = 0;

    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        Queue<Integer> queue = new Queue<>();
        marked[sourceAdd] = true;
        queue.enqueue(sourceAdd);
        announce();

        while (!queue.isEmpty()) {
           int cur = queue.dequeue();

           for (int i : maze.adj(cur)) {
               if (i == targetAdd) {
                   targetFound = true;
               }
               if (!marked[i]) {
                   edgeTo[i] = cur;
                   distTo[i] = distTo[cur] + 1;
                   marked[i] = true;
                   queue.enqueue(i);
                   announce();
               }
               if (targetFound) {
                   return;
               }
           }

       }
    }


    @Override
    public void solve() {
        bfs();
    }
}

