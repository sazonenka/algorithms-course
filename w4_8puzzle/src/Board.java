/*----------------------------------------------------------------
 *  Author:        Aliaksandr Sazonenka
 *  Written:       9/7/2012
 *  Last updated:  9/7/2012
 *
 *  A model of N-by-N board.
 *
 *----------------------------------------------------------------*/

import java.util.Arrays;

public class Board {

    private final int[][] blocks; // the cells in the board
    private final int n;          // the size of the board
    private final int manhattan;  // the sum of the Manhattan distances
                                  // from the blocks to their goal positions

    /**
     * Constructs a board from an N-by-N array of blocks
     * (where blocks[i][j] = block in row i, column j).
     *
     * @param blocks the cells in the board.
     */
    public Board(int[][] blocks) {
        this.blocks = copyBlocks(blocks);
        this.n = blocks.length;
        this.manhattan = internalManhattan();
    }

    /**
     * Retuns the board size.
     */
    public int dimension() {
        return n;
    }

    /**
     * Returns the number of blocks out of place.
     */
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int actualValue = blocks[i][j];
                int expectedValue = (i * n + j + 1) % (n * n);

                if (actualValue > 0 && actualValue != expectedValue) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    /**
     * Returns sum of Manhattan distances between blocks and goal.
     */
    public int manhattan() {
        return manhattan;
    }

    private int internalManhattan() {
        int sumManhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int actualValue = blocks[i][j];
                if (actualValue > 0) {
                    int expectedI = (actualValue - 1) / n;
                    int expectedJ = (actualValue - 1) % n;

                    int distance = Math.abs(expectedI - i)
                                 + Math.abs(expectedJ - j);
                    sumManhattan += distance;
                }
            }
        }
        return sumManhattan;
    }

    /**
     * Is this board the goal board?
     */
    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int actualValue = blocks[i][j];
                int expectedValue = (i * n + j + 1) % (n * n);

                if (actualValue != expectedValue) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns a board obtained by exchanging two adjacent blocks
     * in the same row.
     */
    public Board twin() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (blocks[i][j] > 0 && blocks[i][j + 1] > 0) {
                    return twinBoard(this, i, j, i, j + 1);
                }
            }
        }
        return null;
    }

    /**
     * Returns all neighboring boards.
     */
    public Iterable<Board> neighbors() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    Queue<Board> neighbors = new Queue<Board>();

                    if (i > 0) { // Top neighbor
                        neighbors.enqueue(twinBoard(this, i, j, i - 1, j));
                    }
                    if (i < n - 1) { // Bottom neighbor
                        neighbors.enqueue(twinBoard(this, i, j, i + 1, j));
                    }
                    if (j > 0) { // Left neighbor
                        neighbors.enqueue(twinBoard(this, i, j, i, j - 1));
                    }
                    if (j < n - 1) { // Right neighbor
                        neighbors.enqueue(twinBoard(this, i, j, i, j + 1));
                    }

                    return neighbors;
                }
            }
        }
        return null;
    }

    private Board twinBoard(Board board, int i1, int j1, int i2, int j2) {
        int[][] twin = copyBlocks(board.blocks);
        exch(twin, i1, j1, i2, j2);

        return new Board(twin);
    }

    private int[][] copyBlocks(int[][] a) {
        int length = a.length;

        int[][] copy = new int[length][length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(a[i], 0, copy[i], 0, length);
        }
        return copy;
    }

    private void exch(int[][] a, int i1, int j1, int i2, int j2) {
        int temp = a[i1][j1];
        a[i1][j1] = a[i2][j2];
        a[i2][j2] = temp;
    }

    @Override
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;

        if (y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        return Arrays.deepEquals(this.blocks, that.blocks);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(String.format("%2d ", blocks[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
