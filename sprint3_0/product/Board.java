package sprint3_0.product;

public class Board {
    private Cell[][] grid;

    public Board(int size) {
        grid = new Cell[size][size];
    }

    private static class Cell {
        char letter; // 'S' or 'O'
        char player; // 'X' or 'O'

        Cell(char letter, char player) {
            this.letter = letter;
            this.player = player;
        }
    }

    public char getCell(int row, int column) {
        return grid[row][column] == null ? '\0' : grid[row][column].letter;
    }

    public char getPlayer(int row, int column) {
        return grid[row][column] == null ? '\0' : grid[row][column].player;
    }

    public void makeMove(int row, int column, char letter, char player) {
        if (grid[row][column] == null) {
            grid[row][column] = new Cell(letter, player);
        }
    }

    public int getSize() {
        return grid.length;
    }
}