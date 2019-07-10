package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] STATS;

    /**perform T independent experiments on an N-by-N grid
     *
     * @param N
     * @param T
     * @param pf
     */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        STATS =  new double[T];

        for (int i = 0; i < T; i += 1) {
            Percolation per = pf.make(N);

            while (per.percolates()) {
                int randomRow = StdRandom.uniform(0, N - 1);
                int randomCol = StdRandom.uniform(0, N - 1);
                if (!per.isOpen(randomRow, randomCol)) {
                    per.open(randomRow, randomCol);
                }
                if (per.percolates()) {
                    break;
                }
            }
            STATS[i] = (double) per.numberOfOpenSites() / (N * N);
        }
    }

    /** sample mean of percolation threshold
     *
     * @return
     */
    public double mean() {
        return StdStats.mean(STATS);

    }

    /**sample standard deviation of percolation threshold
     *
     * @return
     */
    public double stddev() {
        return StdStats.stddev(STATS);
    }

    /**low endpoint of 95% confidence interval
     *
     * @return
     */
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(STATS.length);
    }

    /**high endpoint of 95% confidence interval
     *
     * @return
     */
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(STATS.length);
    }
}
