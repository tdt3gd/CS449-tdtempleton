package sprint4_0.product;

import java.util.List;

public class SimpleGame extends SOSGame {
    private boolean gameOver = false;
    private String winner = null;

    public SimpleGame(int size) {
        super(size);
    }

    @Override
    public boolean makeMove(int row, int col, char letter) {
        if (super.makeMove(row, col, letter)) {
            List<ScoredSequence> newSequences = findAllSOS(row, col, currentPlayer);
            if (!newSequences.isEmpty()) {
                scoredSequences.addAll(newSequences);
                gameOver = true;
                winner = currentPlayer;
            } else if (isBoardFull()) {
                gameOver = true;
                winner = "Draw";
            } else {
                switchPlayer();
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
        return winner == null ? "None" : winner;
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