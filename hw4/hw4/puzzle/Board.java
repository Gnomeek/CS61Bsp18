package hw4.puzzle;
import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    private int[][] tile;
    private int[][] goal;
    private int size;

    public Board(int[][] tiles) {
        size = tiles.length;
        tile = new int[size][size];
        goal = new int[size][size];

        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                if (tiles[i][j] < 0 || tiles[i][j] > size * size - 1) {
                    throw new java.lang.IndexOutOfBoundsException();
                }
                tile[i][j] = tiles[i][j];
                goal[i][j] = i * size + j + 1;
            }
        }
        goal[size - 1][size - 1] = 0;
    }

    public int tileAt(int i, int j) {
        return tile[i][j];
    }

    public int size() {
        return size;
    }

    /**
     * Returns neighbors of this board.
     * @cite Josh Hug
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = 0;
                }
            }
        }
        return neighbors;
    }

    public int hamming() {
        int distance = 0;
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                if (tileAt(i, j) != goal[i][j] && tileAt(i, j) != 0) {
                    distance += 1;
                }
            }
        }
        return distance;
    }

    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                int curTile = tileAt(i, j);
                if (curTile != 0) {
                    int idealPositionX = (curTile - 1) / size;
                    int idealPositionY = (curTile - 1) % size;
                    distance += (Math.abs(idealPositionX - i) + Math.abs(idealPositionY - j));
                }
            }
        }
        return distance;
    }

    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || this.getClass() != y.getClass()) {
            return false;
        }

        Board comparedBoard = (Board) y;
        if (this.size() != comparedBoard.size()) {
            return false;
        }
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                if (this.tileAt(i, j) != comparedBoard.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = tile != null ? tile.hashCode() : 0;
        result = result * 31 + goal.hashCode();
        return result;
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
