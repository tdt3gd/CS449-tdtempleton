package sprint3_0.product;

import java.util.ArrayList;
import java.util.List;

public abstract class SOSGame {
    protected Board board;
    protected String currentPlayer;
    protected int boardSize;
    protected List<ScoredSequence> scoredSequences = new ArrayList<>();

    public SOSGame(int size) {
        if (size < 3 || size > 8) {
            throw new IllegalArgumentException("Board size must be between 3 and 8.");
        }
        this.boardSize = size;
        this.board = new Board(size);
        this.currentPlayer = "Blue";
    }

    public Board getBoard() {
        return board;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public int getSize() {
        return boardSize;
    }

    public void switchPlayer() {
        currentPlayer = currentPlayer.equals("Blue") ? "Red" : "Blue";
    }

    public boolean makeMove(int row, int col, char letter) {
        if (row < 0 || row >= boardSize || col < 0 || col >= boardSize) return false;
        if (board.getCell(row, col) == '\0') {
            board.makeMove(row, col, letter, currentPlayer);
            return true;
        }
        return false;
    }

    public List<ScoredSequence> getScoredSequences() {
        return scoredSequences;
    }

    protected List<ScoredSequence> findAllSOS(int row, int col, String player) {
        List<ScoredSequence> sequences = new ArrayList<>();
        char letter = board.getCell(row, col);
        if (letter != 'S') return sequences;

        int[][] directions = {
            {-1, 0}, {1, 0},     // vertical
            {0, -1}, {0, 1},     // horizontal
            {-1, -1}, {1, 1},    // diagonal UL-LR and LR-UL
            {-1, 1}, {1, -1}     // diagonal UR-LL and LL-UR
        };

        for (int[] dir : directions) {
            int r1 = row + dir[0];
            int c1 = col + dir[1];
            int r2 = row + 2 * dir[0];
            int c2 = col + 2 * dir[1];

            if (isInBounds(r1, c1) && isInBounds(r2, c2)) {
                if (board.getCell(r1, c1) == 'O' && board.getCell(r2, c2) == 'S') {
                    sequences.add(new ScoredSequence(row, col, r2, c2, player));
                }
            }
        }
        return sequences;
    }

    protected boolean isInBounds(int row, int col) {
        return row >= 0 && row < boardSize && col >= 0 && col < boardSize;
    }

    public abstract boolean isGameOver();
    public abstract String getWinner();
}