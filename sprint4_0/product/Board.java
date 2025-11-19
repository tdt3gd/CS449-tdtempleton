package sprint4_0.product;

public class Board {
    private final char[][] grid;

    public Board(int size) {
        if (size < 3) {
            throw new IllegalArgumentException("Board size must be at least 3.");
        }
        grid = new char[size][size];
    }

    public int getSize() {
        return grid.length;
    }

    public char getCell(int row, int col) {
        return grid[row][col];
    }

    public boolean isCellEmpty(int row, int col) {
        return grid[row][col] == '\0';
    }

    public boolean placeLetter(int row, int col, char letter) {
        if (isCellEmpty(row, col)) {
            grid[row][col] = letter;
            return true;
        }
        return false;
    }

    public boolean isFull() {
        for (char[] row : grid) {
            for (char cell : row) {
                if (cell == '\0') return false;
            }
        }
        return true;
    }

    public char[][] getGrid() {
        return grid;
    }
 // For testing purposes only
    public void setCell(int row, int col, char letter) {
        grid[row][col] = letter;
    }
}