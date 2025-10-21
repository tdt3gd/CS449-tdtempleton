package sprint2_0.product;

public class GameController {
    private Board board;
    private char currentTurn = 'X'; // 'X' for Blue, 'O' for Red

    public enum GameMode { SIMPLE, GENERAL }
    private GameMode gameMode = GameMode.SIMPLE;

    public GameController(int size) {
        if (size < 3 || size > 8) {
            throw new IllegalArgumentException("Board size must be between 3 and 8.");
        }
        board = new Board(size);
    }

    public void setGameMode(GameMode mode) {
        this.gameMode = mode;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public Board getBoard() {
        return board;
    }

    public char getCurrentTurn() {
        return currentTurn;
    }

    public boolean makeMove(int row, int col, char letter) {
        if (board.getCell(row, col) == '\0') {
            board.makeMove(row, col, letter, currentTurn);
            if (gameMode == GameMode.SIMPLE || gameMode == GameMode.GENERAL) {
                switchTurn();
            }
            return true;
        }
        return false;
    }

    private void switchTurn() {
        currentTurn = (currentTurn == 'X') ? 'O' : 'X';
    }
}