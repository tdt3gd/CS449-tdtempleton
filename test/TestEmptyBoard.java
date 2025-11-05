package sprint4_0.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sprint3_0.product.Board;

import static org.junit.Assert.*;

public class TestEmptyBoard {
    private Board board;

    @Before
    public void setUp() {
        board = new Board(3);
    }

    @After
    public void tearDown() {
        board = null;
    }

    @Test
    public void testNewBoardIsEmpty() {
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                assertEquals('\0', board.getCell(row, col));
                assertNull(board.getOwner(row, col));
            }
        }
    }
}