package hw4.puzzle;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private Stack<WorldState> moved;

    /** Constructor */
    public Solver(WorldState initial) {
        moved = new Stack<>();

        MinPQ<SearchNode> tracker = new MinPQ<>();
        tracker.insert(new SearchNode(initial, 0, null));

        SearchNode goal = null;

        while(!tracker.isEmpty()) {
            SearchNode min = tracker.delMin();
            WorldState minWorldState = min.world();
            SearchNode minPrev = min.prev();
            if (minWorldState.isGoal()) {
                goal = min;
                break;
            } else {
                for (WorldState i : minWorldState.neighbors()) {
                    if (minPrev == null || (minPrev != null && !i.equals(minPrev.world))) {
                        tracker.insert(new SearchNode(i, min.moves() + 1, min));
                    }
                }
            }

            while (goal != null) {
                moved.push(goal.world);
                goal = goal.prev;
            }
        }
    }

    public class SearchNode implements Comparable<SearchNode>{
        private WorldState world;
        private int moves;
        private SearchNode prev;
        private int priority;

        public SearchNode(WorldState ws, int m, SearchNode p) {
            world = ws;
            moves = m;
            prev = p;
            priority = moves + ws.estimatedDistanceToGoal();
        }

        public int compareTo(SearchNode o) {
           return this.priority - o.priority;
        }

        public WorldState world() {
            return world;
        }

        public int moves() {
            return moves;
        }

        public SearchNode prev() {
            return prev;
        }
    }

    public int moves() {
        return moved.size() - 1;
    }


    public Iterable<WorldState> solution() {
        return moved;
    }
}
