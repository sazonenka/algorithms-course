/*----------------------------------------------------------------
 *  Author:        Aliaksandr Sazonenka
 *  Written:       8/19/2012
 *  Last updated:  8/19/2012
 *
 *  Dependencies: Percolation.java
 *
 *  Performer of a series of computational experiments
 *
 *----------------------------------------------------------------*/

public class PercolationStats {

    private double[] thresholds; // Percolation thresholds

    /**
     * Performs t experiments on a n x n percolation system.
     *
     * @param n the size of the grid
     * @param t the number of experiments
     */
    public PercolationStats(int n, int t) {
        if (n <= 0) {
            throw new IllegalArgumentException("Only positive N allowed");
        }
        if (t <= 0) {
            throw new IllegalArgumentException("Only positive T allowed");
        }

        this.thresholds = new double[t];
        for (int i = 0; i < t; ++i) {
            Percolation percolation = new Percolation(n);

            int openedSites = 0;
            while (true) {
                int rowIndex = StdRandom.uniform(n) + 1;
                int columnIndex = StdRandom.uniform(n) + 1;

                if (!percolation.isOpen(rowIndex, columnIndex)) {
                    percolation.open(rowIndex, columnIndex);
                    openedSites++;

                    if (percolation.percolates()) {
                        this.thresholds[i] = ((double) openedSites) / (n * n);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Returns the sample mean.
     */
    public double mean() {
        return StdStats.mean(thresholds);
    }

    /**
     * Returns the sample standard deviation.
     */
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    /**
     * Returns the 95% confidence interval.
     */
    private double[] confidence95Interval() {
        double mean = mean();
        double stddev = stddev();

        double[] interval = new double[2];
        interval[0] = mean - 1.96 * stddev / Math.sqrt(thresholds.length);
        interval[1] = mean + 1.96 * stddev / Math.sqrt(thresholds.length);
        return interval;
    }

    private void printMean() {
        StdOut.println("mean\t\t\t\t\t= " + mean());
    }

    private void printStdDev() {
        StdOut.println("stddev\t\t\t\t\t= " + stddev());
    }

    private void printConfidence95Interval() {
        double[] interval = confidence95Interval();
        StdOut.println("95% confidence interval\t= "
                + interval[0] + ", " + interval[1]);
    }

    /**
     * Client of the class.
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Provide two arguments");
        }

        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, t);
        stats.printMean();
        stats.printStdDev();
        stats.printConfidence95Interval();
    }

}
