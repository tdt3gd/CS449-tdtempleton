package sprint5_0.product;

import java.util.List;

public class SimpleGame extends SOSGame {
    private boolean gameOver = false;
    private String winner = null;

    public SimpleGame(int size) {
        super(size);
    }

    @Override
    protected void handleScoring(List<ScoredSequence> newSequences) {
        if (!newSequences.isEmpty()) {
            gameOver = true;
            winner = currentPlayer;
        } else {
            switchPlayer();
        }
    }

    @Override
    public boolean isGameOver() {
        return gameOver || board.isFull();
    }

    @Override
    public String getWinner() {
        if (gameOver) return winner;
        if (board.isFull()) return "Draw";
        return null;
    }

    @Override
    protected String determineWinner() {
        return scoredSequences.isEmpty() ? "Draw" : winner;
    }
}