package lab11.graphs;

import edu.princeton.cs.algs4.Queue;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return -1;
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        marked[s] = true;
        Queue<Integer> openQueue = new Queue<>();
        Queue<Integer> closeQueue = new Queue<>();
        openQueue.enqueue(s);
        announce();

        while (!openQueue.isEmpty()) {
            int cur = openQueue.dequeue();

            for (int i : maze.adj(cur)) {
                distTo[i] = distTo[cur] + manhattanDistance(cur, t);
                int temp = distTo[]
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

    private int manhattanDistance(int n, int p) {
        int sourceX = maze.toX(n);
        int sourceY = maze.toY(n);
        int targetX = maze.toX(p);
        int targetY = maze.toY(p);
        return Math.abs(sourceX - targetX) + Math.abs(sourceY - targetY);
    }

}

