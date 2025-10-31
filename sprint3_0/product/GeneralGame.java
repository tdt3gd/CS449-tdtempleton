package sprint3_0.product;

import java.util.List;

public class GeneralGame extends SOSGame {
    private int blueScore = 0;
    private int redScore = 0;
    private boolean gameOver = false;

    public GeneralGame(int size) {
        super(size);
    }

    @Override
    public boolean makeMove(int row, int col, char letter) {
        if (super.makeMove(row, col, letter)) {
            List<ScoredSequence> newSequences = findAllSOS(row, col);
            if (!newSequences.isEmpty()) {
                scoredSequences.addAll(newSequences);
                if (currentPlayer == 'X') {
                    blueScore += newSequences.size();
                } else {
                    redScore += newSequences.size();
                }
                // Player keeps turn
            } else {
                switchPlayer();
            }

            if (isBoardFull()) {
                gameOver = true;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public String getWinner() {
        if (!gameOver) return "None";
        if (blueScore > redScore) return "Blue";
        if (redScore > blueScore) return "Red";
        return "Draw";
    }

    public int getScore(char player) {
        return (player == 'X') ? blueScore : redScore;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (board.getCell(row, col) == '\0') return false;
            }
        }
        return true;
    }
}