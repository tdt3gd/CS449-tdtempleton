package sprint3_0.test;

import org.junit.Before;
import org.junit.Test;
import sprint3_0.product.SimpleGame;
import sprint3_0.product.GeneralGame;

import static org.junit.Assert.*;

public class TestGameMoves {
    private SimpleGame simpleGame;
    private GeneralGame generalGame;

    @Before
    public void setUp() {
        simpleGame = new SimpleGame(5);
        generalGame = new GeneralGame(5);
    }

    @Test
    public void testSuccessfulMoveInSimpleGame() {
        boolean moveMade = simpleGame.makeMove(2, 2, 'S');
        assertTrue(moveMade);
        assertEquals('S', simpleGame.getBoard().getCell(2, 2));
        assertEquals("Blue", simpleGame.getBoard().getOwner(2, 2));
    }

    @Test
    public void testUnsuccessfulMoveInSimpleGame() {
        simpleGame.makeMove(1, 1, 'O');
        boolean moveMade = simpleGame.makeMove(1, 1, 'S');
        assertFalse(moveMade);
        assertEquals('O', simpleGame.getBoard().getCell(1, 1));
    }

    @Test
    public void testSuccessfulMoveInGeneralGame() {
        boolean moveMade = generalGame.makeMove(3, 3, 'O');
        assertTrue(moveMade);
        assertEquals('O', generalGame.getBoard().getCell(3, 3));
        assertEquals("Blue", generalGame.getBoard().getOwner(3, 3));
    }

    @Test
    public void testUnsuccessfulMoveInGeneralGame() {
        generalGame.makeMove(0, 0, 'S');
        boolean moveMade = generalGame.makeMove(0, 0, 'O');
        assertFalse(moveMade);
        assertEquals('S', generalGame.getBoard().getCell(0, 0));
    }
}