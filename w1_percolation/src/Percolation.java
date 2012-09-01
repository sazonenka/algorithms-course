/*----------------------------------------------------------------
 *  Author:        Aliaksandr Sazonenka
 *  Written:       8/19/2012
 *  Last updated:  8/19/2012
 *
 *  Dependencies: WeightedQuickUnionUF.java
 *
 *  Model of a percolation system
 *
 *----------------------------------------------------------------*/

public class Percolation {

    private static final int VIRTUAL_SITES = 1;

    private int n; // A percolation system is modeled using an n x n grid
    private int size; // Total number of sites in the system
                      // plus virtual sites

    private boolean[] siteOpened; // Indicated if a site is opened
    private boolean[] siteConnectedToBottom; // Indicated if a site is
                                             // connected to the bottom row
    private WeightedQuickUnionUF siteUnioned; // Implementation of a
                                              // weighted quick-union
                                              // algorithm

    /**
     * Creates an empty percolation system with all sites blocked.
     *
     * @param n the size of the grid
     */
    public Percolation(int n) {
        this.n = n;
        this.size = n * n + VIRTUAL_SITES;

        this.siteOpened = new boolean[this.size];
        this.siteConnectedToBottom = new boolean[this.size];
        this.siteUnioned = new WeightedQuickUnionUF(this.size);

        this.siteOpened[0] = true;
        for (int j = 1; j <= n; j++) {
            int p = flattenIndexes(n, j);
            this.siteConnectedToBottom[p] = true;
        }
    }

    /**
     * Opens the site if it is not already.
     *
     * @param i row index (1 <= i <= n)
     * @param j column index (1 <= j <= n)
     */
    public void open(int i, int j) {
        int p = flattenIndexes(i, j);

        if (!siteOpened[p]) {
            siteOpened[p] = true;

            // Connect to the top virtual site
            if (i == 1) union(p, 0);

            if (i > 1) { // Connect to the top site
                int q = flattenIndexes(i - 1, j);
                if (siteOpened[q]) union(p, q);
            }
            if (i < n) { // Connect to the bottom site
                int q = flattenIndexes(i + 1, j);
                if (siteOpened[q]) union(p, q);
            }
            if (j > 1) { // Connect to the left site
                int q = flattenIndexes(i, j - 1);
                if (siteOpened[q]) union(p, q);
            }
            if (j < n) { // Connect to the right site
                int q = flattenIndexes(i, j + 1);
                if (siteOpened[q]) union(p, q);
            }
        }
    }

    /**
     * Checks if the site is open.
     *
     * @param i row index (1 <= i <= n)
     * @param j column index (1 <= j <= n)
     */
    public boolean isOpen(int i, int j) {
        int p = flattenIndexes(i, j);
        return siteOpened[p];
    }

    /**
     * Checks if the site is full (connected to the top row).
     *
     * @param i row index (1 <= i <= n)
     * @param j column index (1 <= j <= n)
     */
    public boolean isFull(int i, int j) {
        int p = flattenIndexes(i, j);
        return siteOpened[p] && siteUnioned.connected(p, 0);
    }

    /**
     * Checks if the system percolates.
     */
    public boolean percolates() {
        int rootOfVirtualTop = siteUnioned.find(0);
        return siteConnectedToBottom[rootOfVirtualTop];
    }

    private int flattenIndexes(int i, int j) {
        if (i < 1 || i > n) {
            throw new IndexOutOfBoundsException("Row index i out of bounds");
        }
        if (j < 1 || j > n) {
            throw new IndexOutOfBoundsException("Column index j out of bounds");
        }
        return (i - 1) * n + j;
    }

    private void union(int p, int q) {
        connectRootsToBottom(p, q);
        siteUnioned.union(p, q);
    }

    private void connectRootsToBottom(int p, int q) {
        int pRoot = siteUnioned.find(p);
        int qRoot = siteUnioned.find(q);

        boolean anyOfRootsConnectedToBottom = siteConnectedToBottom[pRoot]
                || siteConnectedToBottom[qRoot];

        siteConnectedToBottom[pRoot] = anyOfRootsConnectedToBottom;
        siteConnectedToBottom[qRoot] = anyOfRootsConnectedToBottom;
    }

}
