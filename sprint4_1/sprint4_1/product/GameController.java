package sprint4_1.product;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class GameController {
    private final GUI gui;

    public GameController(GUI gui) {
        this.gui = gui;
    }

    // Handle a human player's move
    public void handleMove(int row, int col) {
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
                if (gui.isCurrentPlayerComputer()) triggerComputerMove();
            }
        }
    }

    // Trigger computer move with a delay
    public void triggerComputerMove() {
        PauseTransition aiPause = new PauseTransition(Duration.millis(gui.getSpeedSlider().getValue()));
        aiPause.setOnFinished(ev -> makeComputerMove());
        aiPause.play();
    }

    // Perform the computer move
    private void makeComputerMove() {
        ComputerPlayer ai = gui.getComputerPlayers().get(gui.getGame().getCurrentPlayer());
        if (ai == null) return;

        ai.makeMove(gui.getGame());
        gui.drawNewBoard();

        int[] lastMove = gui.getGame().getLastMove();
        if (lastMove != null) {
            String previousPlayer = gui.getGame().getCurrentPlayer().equals("Blue") ? "Red" : "Blue";
            char letter = gui.getGame().getLastLetter();
            gui.getConsole().printMove(previousPlayer, lastMove[0], lastMove[1], letter);
        }

        if (gui.getGame().isGameOver()) {
            gui.getGameStatus().setText("Game Over: " + gui.getGame().getWinner());
            gui.getConsole().printWinner(gui.getGame().getWinner());
        } else {
            gui.getGameStatus().setText(gui.getGame().getCurrentPlayer() + "'s Turn");
            if (gui.isCurrentPlayerComputer()) triggerComputerMove();
        }
    }
}