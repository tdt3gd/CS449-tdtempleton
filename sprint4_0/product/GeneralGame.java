package sprint4_0.product;

import java.util.List;

public class GeneralGame extends SOSGame {
    private boolean gameOver = false;
    private String winner = null;
    private int blueScore = 0;
    private int redScore = 0;

    public GeneralGame(int size) {
        super(size);
    }

    @Override
    public boolean makeMove(int row, int col, char letter) {
        if (super.makeMove(row, col, letter)) {
            List<ScoredSequence> newSequences = findAllSOS(row, col, currentPlayer);
            if (!newSequences.isEmpty()) {
                scoredSequences.addAll(newSequences);
                if (currentPlayer.equals("Blue")) {
                    blueScore += newSequences.size();
                } else {
                    redScore += newSequences.size();
                }
            }
            if (isBoardFull()) {
                gameOver = true;
                winner = determineWinner();
            } else {
                switchPlayer();
            }
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (board.getCell(row, col) == '\0') return false;
            }
        }
        return true;
    }

    private String determineWinner() {
        if (blueScore > redScore) return "Blue";
        if (redScore > blueScore) return "Red";
        return "Draw";
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public String getWinner() {
        return winner == null ? "None" : winner;
    }

    public int getBlueScore() {
        return blueScore;
    }

    public int getRedScore() {
        return redScore;
    }
}