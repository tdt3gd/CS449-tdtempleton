package sprint2_0.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sprint0_0.product.Board;
import sprint0_0.product.Console;

public class TestBoardConsole {
	private Board board;

	@Before
	public void setUp() throws Exception {
		board = new Board();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEmptyBoard() {
		new Console(board).displayBoard();
	}

}
