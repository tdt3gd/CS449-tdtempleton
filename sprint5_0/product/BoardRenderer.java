package sprint5_0.product;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class BoardRenderer {
    private final GUI gui;

    public BoardRenderer(GUI gui) {
        this.gui = gui;
    }

    public void drawNewBoard() {
        // Reset the squares array
        gui.resetSquares();

        // Clear board and line layers
        gui.getBoardPane().getChildren().clear();
        gui.getLineLayer().getChildren().clear();

        // Populate board with squares and render cells
        populateBoard();

        // Update scores
        gui.updateScores();

        // Render scored sequences (lines between SOS)
        renderScoredSequences();
    }

    private void populateBoard() {
        for (int row = 0; row < gui.getGridSize(); row++) {
            for (int col = 0; col < gui.getGridSize(); col++) {
                Square square = gui.createSquare(row, col);
                char cell = gui.getGame().getBoard().getCell(row, col);
                gui.renderCell(square, cell);
            }
        }
    }

    private void renderScoredSequences() {
        PauseTransition pause = new PauseTransition(Duration.millis(50));
        pause.setOnFinished(e -> gui.renderScoredSequences());
        pause.play();
    }
}