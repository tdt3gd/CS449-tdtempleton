package sprint4_2.product;

import java.util.List;

public class GeneralGame extends SOSGame {
    private int blueScore = 0;
    private int redScore = 0;

    public GeneralGame(int size) {
        super(size);
    }

    @Override
    protected void handleScoring(List<ScoredSequence> newSequences) {
        if (!newSequences.isEmpty()) {
            int points = newSequences.size();
            if ("Blue".equals(currentPlayer)) {
                blueScore += points;
            } else {
                redScore += points;
            }
        }
        switchPlayer(); // Always switch, even after scoring
    }

    @Override
    protected String determineWinner() {
        if (blueScore > redScore) return "Blue";
        if (redScore > blueScore) return "Red";
        return "Draw";
    }

    public int getBlueScore() {
        return blueScore;
    }

    public int getRedScore() {
        return redScore;
    }
}