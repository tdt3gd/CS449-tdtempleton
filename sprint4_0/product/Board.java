package sprint4_0.product;

public class Board {
    private final int size;
    private final char[][] grid;
    private final String[][] owners;

    public Board(int size) {
        this.size = size;
        this.grid = new char[size][size];
        this.owners = new String[size][size];
    }

    public int getSize() {
        return size;
    }

    public char getCell(int row, int col) {
        return grid[row][col];
    }

    public String getOwner(int row, int col) {
        return owners[row][col];
    }

    public boolean makeMove(int row, int col, char letter, String player) {
        if (row < 0 || row >= size || col < 0 || col >= size) return false;
        if (grid[row][col] != '\0') return false;

        grid[row][col] = letter;
        owners[row][col] = player;
        return true;
    }

    public void reset() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                grid[row][col] = '\0';
                owners[row][col] = null;
            }
        }
    }
}