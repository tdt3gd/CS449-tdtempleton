package sprint3_0.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sprint3_0.product.SimpleGame;
import sprint3_0.product.GeneralGame;

import static org.junit.Assert.*;

public class TestGameEndConditions {

    private SimpleGame simpleGame;
    private GeneralGame generalGame;

    @Before
    public void setUp() {
        // Each test initializes its own game
    }

    @After
    public void tearDown() {
        simpleGame = null;
        generalGame = null;
    }

    // --- User Story 5: SimpleGame ---

    @Test
    public void testSimpleGameEndsOnFirstSOS_Horizontal() {
        simpleGame = new SimpleGame(3);
        simpleGame.makeMove(0, 0, 'S'); // Blue
        simpleGame.makeMove(1, 0, 'S'); // Red
        simpleGame.makeMove(0, 1, 'O'); // Blue
        simpleGame.makeMove(1, 1, 'O'); // Red
        simpleGame.makeMove(0, 2, 'S'); // Blue completes SOS

        assertTrue(simpleGame.isGameOver());
        assertEquals("Blue", simpleGame.getWinner());
        assertEquals(1, simpleGame.getScoredSequences().size());
    }

    @Test
    public void testSimpleGameEndsInDrawWhenBoardIsFull_NoSOS() {
        simpleGame = new SimpleGame(3);
        simpleGame.makeMove(0, 0, 'S'); // Blue
        simpleGame.makeMove(0, 1, 'O'); // Red
        simpleGame.makeMove(0, 2, 'O'); // Blue
        simpleGame.makeMove(1, 0, 'O'); // Red
        simpleGame.makeMove(1, 1, 'S'); // Blue
        simpleGame.makeMove(1, 2, 'O'); // Red
        simpleGame.makeMove(2, 0, 'O'); // Blue
        simpleGame.makeMove(2, 1, 'S'); // Red
        simpleGame.makeMove(2, 2, 'S'); // Blue

        assertTrue(simpleGame.isGameOver());
        assertEquals("Draw", simpleGame.getWinner());
        assertEquals(0, simpleGame.getScoredSequences().size());
    }

    // --- User Story 7: GeneralGame ---

    @Test
    public void testGeneralGameEndsAfterBoardFull_WinnerByScore() {
        generalGame = new GeneralGame(3);
        generalGame.makeMove(0, 0, 'S'); // Blue
        generalGame.makeMove(0, 1, 'O'); // Red
        generalGame.makeMove(0, 2, 'S'); // Blue scores 1

        generalGame.makeMove(1, 0, 'S'); // Blue
        generalGame.makeMove(1, 1, 'O'); // Red
        generalGame.makeMove(1, 2, 'S'); // Blue scores 1

        generalGame.makeMove(2, 0, 'O'); // Blue
        generalGame.makeMove(2, 1, 'S'); // Red
        generalGame.makeMove(2, 2, 'O'); // Blue

        assertTrue(generalGame.isGameOver());
        assertEquals("Blue", generalGame.getWinner());
        assertEquals(2, generalGame.getScore("Blue"));
        assertEquals(0, generalGame.getScore("Red"));
    }

    @Test
    public void testGeneralGameEndsInDrawWhenScoresEqual() {
        generalGame = new GeneralGame(3);

        // Blue scores one SOS horizontally at top row
        generalGame.makeMove(0, 0, 'S'); // Blue
        generalGame.makeMove(1, 0, 'O'); // Red
        generalGame.makeMove(0, 1, 'O'); // Blue
        generalGame.makeMove(1, 1, 'S'); // Red
        generalGame.makeMove(0, 2, 'S'); // Blue → SOS at (0,0)-(0,1)-(0,2)

        // Red scores one SOS vertically at right column
        generalGame.makeMove(2, 0, 'O'); // Blue
        generalGame.makeMove(1, 2, 'O'); // Red
        generalGame.makeMove(2, 1, 'S'); // Blue
        generalGame.makeMove(2, 2, 'S'); // Red → SOS at (0,2)-(1,2)-(2,2)

        assertTrue(generalGame.isGameOver());
        assertEquals(1, generalGame.getScore("Blue"));
        assertEquals(1, generalGame.getScore("Red"));
        assertEquals("Draw", generalGame.getWinner());
    }
}