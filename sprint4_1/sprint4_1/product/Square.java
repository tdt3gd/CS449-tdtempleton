package sprint4_1.product;

import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class Square extends StackPane {
    private final int row;
    private final int col;
    private final GUI gui;

    public Square(int row, int col, GUI gui) {
        this.row = row;
        this.col = col;
        this.gui = gui;

        setPrefSize(60, 60);
        setStyle("-fx-border-color: black; -fx-background-color: white;");
        setOnMouseClicked(e -> handleClick());
    }

    private void handleClick() {
        if (gui.getGame().isGameOver()) return;
        if (gui.isCurrentPlayerComputer()) return;

        char letter = gui.getSelectedLetterForCurrentPlayer();
        String player = gui.getGame().getCurrentPlayer();

        if (gui.getGame().makeMove(row, col, letter)) {
            gui.drawNewBoard();
            gui.getConsole().printMove(player, row, col, letter);

            if (gui.getGame().isGameOver()) {
                gui.getGameStatus().setText("Game Over: " + gui.getGame().getWinner());
                gui.getConsole().printWinner(gui.getGame().getWinner());
            } else {
                gui.getGameStatus().setText(gui.getGame().getCurrentPlayer() + "'s Turn");
                if (gui.isCurrentPlayerComputer()) {
                    PauseTransition aiPause = new PauseTransition(Duration.millis(gui.getSpeedSlider().getValue()));
                    aiPause.setOnFinished(ev -> gui.makeComputerMove());
                    aiPause.play();
                }
            }
        }
    }

    public void drawCross() {
        Label label = new Label("S");
        label.setStyle("-fx-font-size: 24px; -fx-text-fill: black;");
        getChildren().add(label);
    }

    public void drawNought() {
        Label label = new Label("O");
        label.setStyle("-fx-font-size: 24px; -fx-text-fill: black;");
        getChildren().add(label);
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
}