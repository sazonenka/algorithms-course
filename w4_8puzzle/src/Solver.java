/*----------------------------------------------------------------
 *  Author:        Aliaksandr Sazonenka
 *  Written:       9/7/2012
 *  Last updated:  9/7/2012
 *
 *----------------------------------------------------------------*/

public class Solver {

    private MinPQ<SearchNode> search1;
    private MinPQ<SearchNode> search2;

    private SearchNode goal;
    private int moves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        this.search1 = new MinPQ<SearchNode>();
        this.search2 = new MinPQ<SearchNode>();

        this.goal = null;
        this.moves = 0;

        solve(initial);
    }

    private void solve(Board initial) {
        search1.insert(new SearchNode(initial, null, moves));
        search2.insert(new SearchNode(initial.twin(), null, moves));

        while (true) {
            SearchNode node1 = search1.delMin();
            Board board1 = node1.getBoard();
            if (board1.isGoal()) {
                goal = node1;
                break;
            }

            SearchNode node2 = search2.delMin();
            Board board2 = node2.getBoard();
            if (board2.isGoal()) {
                break;
            }

            moves++;

            for (Board neighbor : board1.neighbors()) {
                search1.insert(new SearchNode(neighbor, node1, moves));
            }

            for (Board neighbor : board2.neighbors()) {
                search2.insert(new SearchNode(neighbor, node2, moves));
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return goal != null;
    }

    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (!isSolvable()) return -1;

        return moves;
    }

    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        Stack<Board> solution = new Stack<Board>();
        for (SearchNode node = goal; node != null; node = node.getPrevious()) {
            solution.push(node.getBoard());
        }
        return solution;
    }

    // solve a slider puzzle (given below)
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

    private class SearchNode implements Comparable<SearchNode> {

        private final Board board;
        private final SearchNode previous;
        private final int movesMade;

        public SearchNode(Board board, SearchNode previous, int movesMade) {
            this.board = board;
            this.previous = previous;
            this.movesMade = movesMade;
        }

        public Board getBoard() { return board; }
        public SearchNode getPrevious() { return previous; }
        public int getMovesMade() { return movesMade; }

        @Override
        public int compareTo(SearchNode that) {
            int m1 = this.board.manhattan();
            int m2 = that.board.manhattan();
            return m1 - m2;
        }

    }

}
