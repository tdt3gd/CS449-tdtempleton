package sprint4_1.product;

public class GameInitializer {

    /**
     * Initialize a new game instance and console.
     *
     * @param gridSize   the size of the board
     * @param isSimple   true for SimpleGame, false for GeneralGame
     * @param gui        reference to the GUI so we can hook console output
     * @return           the initialized SOSGame
     */
    public static SOSGame initGame(int gridSize, boolean isSimple, GUI gui) {
        SOSGame game = isSimple ? new SimpleGame(gridSize) : new GeneralGame(gridSize);

        // Initialize console with board and output callback
        Console console = new Console(game.getBoard(), msg -> gui.getConsoleOutput().appendText(msg + "\n"));
        gui.setConsole(console);

        // Reset squares array
        gui.resetSquares();

        // Draw the board initially
        gui.drawNewBoard();

        return game;
    }
}