package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;

public class Percolation {
    private int RANGE;
    private boolean[] opened;
    private int openedSite;
    private int vituralTop;
    private int vituralBottom;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufWithoutBottom;

    /**convert the two dimensional index to one dimensional
     *
     * @param row
     * @param col
     * @param N
     * @return one dimensional index
     */
    private static int twodConvertToOned(int row, int col, int N) {
        return row * N + col + 1;
    }

    /**find the neighbour of the given site
     *
     * @param row
     * @param col
     * @param N
     * @return given site's neighbour in ArrayList
     */
    private static ArrayList<Integer> findNeighbour(int row, int col, int N) {
        ArrayList<Integer> neighbour = new ArrayList<>();
        //upward neighbour
        if (row > 0) {
            int index = twodConvertToOned(row - 1, col, N);
            neighbour.add(index);
        }

        //downward neighbour
        if (row < N - 1) {
            int index = twodConvertToOned(row + 1, col, N);
            neighbour.add(index);
        }

        //left neighbour
        if (col > 0) {
            int index = twodConvertToOned(row, col - 1, N);
            neighbour.add(index);
        }

        //right neighbour
        if (col < N - 1) {
            int index = twodConvertToOned(row, col + 1, N);
            neighbour.add(index);
        }

        return neighbour;
    }


    /**create N-by-N grid, with all sites initially blocked
     * the first one in uf is virtual top site
     * the last one in uf is virtual bottom site
     * @param N
     */
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        // instantiate opened array
        RANGE = N;
        opened = new boolean[N * N + 2];
        for (int i = 0; i < N * N + 2; i += 1) {
            opened[i] = false;
        }
        openedSite = 0;

        vituralTop = 0;
        vituralBottom = N * N + 1;
        uf = new WeightedQuickUnionUF(N * N + 2);
        ufWithoutBottom = new WeightedQuickUnionUF(N * N + 2);
    }

    /**open the site (row, col) if it is not open already
     * and union the neighbour site if it is possible
     * @param row
     * @param col
     */
    public void open(int row, int col) {
        if (row >= RANGE || col >= RANGE) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (!isOpen(row, col)) {
            int index = twodConvertToOned(row, col, RANGE);
            opened[index] = true;
            openedSite += 1;


            if (RANGE == 1) {
                uf.union(index, vituralTop);
                ufWithoutBottom.union(index, vituralTop);
                uf.union(index, vituralBottom);
            }
            if (row == 0) {
                uf.union(index, vituralTop);
                ufWithoutBottom.union(index, vituralTop);
            } else if (row == RANGE - 1) {
                uf.union(index, vituralBottom);
            }

            ArrayList<Integer> neighbour = findNeighbour(row, col, RANGE);
            for (int s : neighbour) {
                if (opened[s]) {
                    uf.union(index, s);
                    ufWithoutBottom.union(index, s);
                }
            }
        }
    }

    /**return true if the site(row, col) is open, false otherwise
     *
     * @param row
     * @param col
     * @return
     */
    public boolean isOpen(int row, int col) {
        if (row >= RANGE || col >= RANGE) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        int index = twodConvertToOned(row, col, RANGE);
        return opened[index];
    }

    /**return true if the site(row, col) is full, false otherwise
     *
     * @param row
     * @param col
     * @return
     */
    public boolean isFull(int row, int col) {
        if (row >= RANGE || col >= RANGE) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        int index = twodConvertToOned(row, col, RANGE);
        return ufWithoutBottom.connected(index, vituralTop);
    }

    /**total number of the open sites
     *
     * @return
     */
    public int numberOfOpenSites() {
        return openedSite;
    }

    /**return true if the system is percolated, false otherwise
     *
     * @return
     */
    public boolean percolates() {
        return uf.connected(vituralTop, vituralBottom);
    }

    // unit testing
    public static void main(String[] args) {
        Percolation per = new Percolation(4);
        per.open(0, 0);
        System.out.println(per.isFull(0, 0));
        System.out.println(per.isOpen(0, 0));
        System.out.println(per.percolates());
        per.open(1, 1);
        System.out.println(per.isFull(1, 1));
        per.open(0, 2);
        per.open(0, 3);
        System.out.println(per.numberOfOpenSites());
        System.out.println(per.isFull(0, 2));
        System.out.println(per.isOpen(0, 1));
        System.out.println(per.isOpen(0, 2));
    }

}
