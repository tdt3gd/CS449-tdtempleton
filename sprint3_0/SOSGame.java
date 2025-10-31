package sprint3_0.product;

public abstract class SOSGame {
    protected Board board;
    protected char currentPlayer; // 'X' for Blue, 'O' for Red
    protected int boardSize;

    public SOSGame(int size) {
        if (size < 3 || size > 8) {
            throw new IllegalArgumentException("Board size must be between 3 and 8.");
        }
        this.boardSize = size;
        this.board = new Board(size);
        this.currentPlayer = 'X'; // Blue starts
    }

    public Board getBoard() {
        return board;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public int getSize() {
        return boardSize;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    /**
     * Attempts to make a move at the specified location with the given letter.
     * Returns true if the move was successful, false otherwise.
     */
    public boolean makeMove(int row, int col, char letter) {
        if (row < 0 || row >= boardSize || col < 0 || col >= boardSize) {
            return false;
        }
        if (board.getCell(row, col) == '\0') {
            board.makeMove(row, col, letter, currentPlayer);
            return true;
        }
        return false;
    }

    /**
     * Checks whether the game is over.
     * Implemented differently in SimpleGame and GeneralGame.
     */
    public abstract boolean isGameOver();

    /**
     * Returns the winner of the game.
     * "Blue", "Red", or "Draw"
     */
    public abstract String getWinner();

    /**
     * Utility method to check if an SOS is formed at a given cell.
     * Used by both game types.
     */
    protected boolean isSOSFormed(int row, int col) {
        char letter = board.getCell(row, col);
        char player = board.getPlayer(row, col);
        if (letter == '\0') return false;

        // Check all 8 directions for SOS pattern
        int[][] directions = {
            {-1, 0}, {1, 0}, // vertical
            {0, -1}, {0, 1}, // horizontal
            {-1, -1}, {1, 1}, // diagonal \
            {-1, 1}, {1, -1}  // diagonal /
        };

        for (int[] dir : directions) {
            int r1 = row + dir[0];
            int c1 = col + dir[1];
            int r2 = row + 2 * dir[0];
            int c2 = col + 2 * dir[1];

            if (isInBounds(r1, c1) && isInBounds(r2, c2)) {
                char l1 = board.getCell(r1, c1);
                char l2 = board.getCell(r2, c2);
                if (letter == 'S' && l1 == 'O' && l2 == 'S') {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean isInBounds(int row, int col) {
        return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
    }
}