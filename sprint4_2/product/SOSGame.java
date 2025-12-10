package sprint4_2.product;

import java.util.ArrayList;
import java.util.List;

public abstract class SOSGame {
    protected Board board;
    protected String currentPlayer = "Blue";
    protected List<ScoredSequence> scoredSequences = new ArrayList<>();
    protected int[] lastMove = null;
    protected char lastLetter = '\0';

    public SOSGame(int size) {
        if (size < 3) {
            throw new IllegalArgumentException("Game board size must be at least 3.");
        }
        board = new Board(size);
    }

    public Board getBoard() {
        return board;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public List<ScoredSequence> getScoredSequences() {
        return scoredSequences;
    }

    public int[] getLastMove() {
        return lastMove;
    }

    public char getLastLetter() {
        return lastLetter;
    }

    public boolean makeMove(int row, int col, char letter) {
        if (!board.placeLetter(row, col, letter)) return false;
        lastMove = new int[]{row, col};
        lastLetter = letter;
        List<ScoredSequence> newSequences = BoardAnalyzer.checkSOS(board, row, col, currentPlayer);
        System.out.println("Checking SOS at (" + row + ", " + col + ") for " + currentPlayer);
        System.out.println("Found " + newSequences.size() + " new sequences.");
        scoredSequences.addAll(newSequences);
        handleScoring(newSequences);
        return true;
    }

    public List<int[]> getAvailableMoves() {
        List<int[]> moves = new ArrayList<>();
        int size = board.getSize();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board.isCellEmpty(row, col)) {
                    moves.add(new int[]{row, col});
                }
            }
        }
        return moves;
    }

    protected abstract void handleScoring(List<ScoredSequence> newSequences);

    protected void switchPlayer() {
        currentPlayer = currentPlayer.equals("Blue") ? "Red" : "Blue";
    }

    public boolean isGameOver() {
        return board.isFull();
    }

    public String getWinner() {
        return isGameOver() ? determineWinner() : null;
    }

    protected abstract String determineWinner();
}