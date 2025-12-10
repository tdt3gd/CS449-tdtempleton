package sprint4_2.product;

import java.util.function.Consumer;

public class Console {
    private final Board board;
    private final Consumer<String> output;

    public Console(Board board, Consumer<String> output) {
        this.board = board;
        this.output = output;
    }

    public void printMove(String player, int row, int col, char letter) {
        output.accept(player + " played '" + letter + "' at (" + row + ", " + col + ")");
    }

    public void printWinner(String winner) {
        if ("Draw".equals(winner)) {
            output.accept("The game ended in a draw.");
        } else {
            output.accept("Winner: " + winner);
        }
    }

    public void printBoard() {
        int size = board.getSize();
        StringBuilder sb = new StringBuilder();

        // Print column headers
        sb.append("  ");
        for (int col = 0; col < size; col++) {
            sb.append(col).append(" ");
        }
        sb.append("\n");

        // Print each row
        for (int row = 0; row < size; row++) {
            sb.append(row).append(" ");
            for (int col = 0; col < size; col++) {
                char cell = board.getCell(row, col);
                sb.append(cell == '\0' ? "." : cell).append(" ");
            }
            sb.append("\n");
        }

        output.accept(sb.toString().trim());
    }
}