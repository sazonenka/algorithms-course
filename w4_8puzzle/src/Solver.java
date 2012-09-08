/*----------------------------------------------------------------
 *  Author:        Aliaksandr Sazonenka
 *  Written:       9/7/2012
 *  Last updated:  9/7/2012
 *
 *  Implementation of the A* algorithm.
 *
 *----------------------------------------------------------------*/

public class Solver {

    private final SearchNode goal; // the target node

    /** Finds a solution to the initial board (using the A* algorithm). */
    public Solver(Board initial) {
        MinPQ<SearchNode> queue1 = new MinPQ<SearchNode>();
        MinPQ<SearchNode> queue2 = new MinPQ<SearchNode>();

        queue1.insert(new SearchNode(initial, null, 0));
        queue2.insert(new SearchNode(initial.twin(), null, 0));

        while (true) {
            SearchNode node1 = queue1.delMin();
            if (node1.getBoard().isGoal()) {
                this.goal = node1;
                break;
            }

            SearchNode node2 = queue2.delMin();
            if (node2.getBoard().isGoal()) {
                this.goal = null;
                break;
            }

            for (Board neighbor : node1.getBoard().neighbors()) {
                SearchNode previous = node1.getPrevious();
                if (previous != null && previous.getBoard().equals(neighbor)) {
                    continue;
                }

                SearchNode newNode = new SearchNode(neighbor, node1,
                        node1.getMovesMade() + 1);
                queue1.insert(newNode);
            }

            for (Board neighbor : node2.getBoard().neighbors()) {
                SearchNode previous = node2.getPrevious();
                if (previous != null && previous.getBoard().equals(neighbor)) {
                    continue;
                }

                SearchNode newNode = new SearchNode(neighbor, node2,
                        node2.getMovesMade() + 1);
                queue2.insert(newNode);
            }
        }
    }

    /** Is the initial board solvable? */
    public boolean isSolvable() {
        return goal != null;
    }

    /**
     * Returns min number of moves to solve initial board;
     * -1 if no solution.
     */
    public int moves() {
        if (!isSolvable()) return -1;
        else               return goal.getMovesMade();
    }

    /**
     * Returns the sequence of boards in a shortest solution;
     * null if no solution.
     */
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        Stack<Board> solution = new Stack<Board>();
        for (SearchNode node = goal; node != null; node = node.getPrevious()) {
            solution.push(node.getBoard());
        }
        return solution;
    }

    /** Solves a slider puzzle. */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();

        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }

    /**
     * A basic structure of A* search.
     */
    private static class SearchNode implements Comparable<SearchNode> {
        private final Board board;          // the current board
        private final SearchNode previous;  // the pointer to the previous node
        private final int movesMade;        // the number of moves made so far

        /** Creates a search node. */
        public SearchNode(Board board, SearchNode previous, int movesMade) {
            this.board = board;
            this.previous = previous;
            this.movesMade = movesMade;
        }

        /** Returns the current board. */
        public Board getBoard() { return board; }
        /** Returns the previous node. */
        public SearchNode getPrevious() { return previous; }
        /** Returns the number of moves made so far. */
        public int getMovesMade() { return movesMade; }

        @Override
        public int compareTo(SearchNode that) {
            int m1 = this.board.manhattan() + this.movesMade;
            int m2 = that.board.manhattan() + that.movesMade;
            return m1 - m2;
        }
    }

}
