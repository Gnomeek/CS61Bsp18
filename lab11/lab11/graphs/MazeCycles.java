package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int sourceAdd;
    private boolean haveCycle = false;
    private int endVertice = -1;
    private int[] helperEdgeTo;

    public MazeCycles(Maze m) {
        super(m);

        helperEdgeTo = new int[m.V()];
        sourceAdd = m.xyTo1D(1, 1);
        helperEdgeTo[sourceAdd] = sourceAdd;
    }

    /**For every visited vertex v, if there is an adjacent u
     * that u is already visited and u is not parent of v,
     * then there is a cycle in graph.
     */
    @Override
    public void solve() {
        helpFindCycle(sourceAdd);
    }

    // Helper methods go here
    public void helpFindCycle(int s) {
        marked[s] = true;
        announce();

        for (int i : maze.adj(s)) {
            if (!marked[i]) {
                helperEdgeTo[i] = s;
                helpFindCycle(i);
            } else if (marked[i] && i != helperEdgeTo[s]) {
                traversalCycle(i, s);
                haveCycle = true;
            }

            if (haveCycle) {
                return;
            }
        }
    }

    private void traversalCycle(int n, int p) {
        edgeTo[n] = p;
        announce();
        endVertice = n;
        int lastVertice = edgeTo[endVertice];
        while (lastVertice != endVertice) {
            edgeTo[lastVertice] =helperEdgeTo[lastVertice];
            announce();
            lastVertice = helperEdgeTo[lastVertice];
        }
    }

}

