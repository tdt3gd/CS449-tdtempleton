package sprint3_0.product;

public class Console {
	private Board board;

	public Console(Board board) {
		this.board = board;
	}

	public void displayBoard() {
		for (int row = 0; row < board.getSize(); row++) {
			System.out.println("-------");
			for (int col = 0; col < board.getSize(); col++) {
				char cell = board.getCell(row, col);
				System.out.print("|" + (cell == '\0' ? ' ' : cell));
			}
			System.out.println("|");
		}
		System.out.println("-------");
	}
	
	public void displayMove(int row, int col) {
		char player = board.getPlayer(row, col);
		char value = board.getCell(row, col);

		String playerName = (player == 'X') ? "Blue player" : "Red player";
		System.out.println(playerName);
		System.out.println("Row: " + row + ", Column: " + col);
		System.out.println("Grid cell value: " + (value == '\0' ? " " : value));
	}

	public static void main(String[] args) {
		Board board = new Board(6);
		new Console(board).displayBoard();
	}
}