package sprint4_0.product;

import java.util.function.Consumer;

public class Console {
    private final Board board;
    private final Consumer<String> output;

    public Console(Board board, Consumer<String> output) {
        this.board = board;
        this.output = output;
    }

    public void printBoard() {
        StringBuilder sb = new StringBuilder();
        int size = board.getSize();
        sb.append("   ");
        for (int col = 0; col < size; col++) {
            sb.append(col).append(" ");
        }
        sb.append("\n");

        for (int row = 0; row < size; row++) {
            sb.append(row).append(" |");
            for (int col = 0; col < size; col++) {
                char cell = board.getCell(row, col);
                sb.append((cell == '\0' ? "." : cell)).append(" ");
            }
            sb.append("\n");
        }
        output.accept(sb.toString());
    }

    public void printMove(int row, int col) {
        char letter = board.getCell(row, col);
        String player = board.getOwner(row, col);
        output.accept(String.format("Player %s placed %c at (%d, %d)", player, letter, row, col));
    }

    public void printWinner(String winner) {
        if (winner.equals("Draw")) {
            output.accept("The game ended in a draw.");
        } else {
            output.accept("Winner: " + winner);
        }
    }
}