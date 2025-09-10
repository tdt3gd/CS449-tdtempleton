package sprint0_0.product;

public class Board {

	private int[][] grid;
	private char turn = 'X';

	public Board() {
		grid = new int[8][8];
	}

	public int getCell(int row, int column) {
		if (row >= 0 && row < 8 && column >= 0 && column < 8)
			return grid[row][column];
		else
			return -1;
	}

	public char getTurn() {
		return turn;
	}

	public void makeMove(int row, int column) {
		if (row >= 0 && row < 8 && column >= 0 && column < 8
				&& grid[row][column] == 0) {
			grid[row][column] = (turn == 'X')? 1 : 2; 
			turn = (turn == 'X')? 'O' : 'X';
			System.out.println("Row: " + row + ", Column: " + column);
			System.out.println("Grid cell value: " + grid[row][column]);
		}
	}
	

}
