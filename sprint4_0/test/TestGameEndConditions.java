package sprint4_0.test;

import org.junit.Test;
import sprint4_0.product.*;

import static org.junit.Assert.*;

public class TestGameEndConditions {

    @Test
    public void testSimpleGameEndsOnFirstSOS_Horizontal() {
        SimpleGame game = new SimpleGame(3);
        game.makeMove(0, 0, 'S');
        game.makeMove(1, 0, 'X');
        game.makeMove(0, 2, 'S');
        game.makeMove(1, 2, 'X');
        game.makeMove(0, 1, 'O'); // triggers SOS

        assertTrue(game.isGameOver());
        assertEquals("Blue", game.getWinner());
        assertEquals(1, game.getScoredSequences().size());
    }

    @Test
    public void testGeneralGameEndsAfterBoardFull_WinnerByScore() {
        GeneralGame game = new GeneralGame(3);

        // Blue scores horizontal SOS
        game.makeMove(0, 0, 'S');
        game.makeMove(1, 0, 'X');
        game.makeMove(0, 2, 'S');
        game.makeMove(1, 2, 'X');
        game.makeMove(0, 1, 'O'); // Blue → 1 SOS

        // Fill remaining cells with non-SOS
        game.makeMove(2, 0, 'X');
        game.makeMove(2, 1, 'X');
        game.makeMove(1, 1, 'X');
        game.makeMove(2, 2, 'X');

        assertTrue(game.isGameOver());
        assertEquals("Blue", game.getWinner());
        assertEquals(1, game.getBlueScore());
        assertEquals(0, game.getRedScore());
    }

    @Test
    public void testGeneralGameEndsInDrawWhenScoresEqual() {
        GeneralGame game = new GeneralGame(3);

        // Blue scores horizontal SOS: (0,0)-(0,1)-(0,2)
        game.makeMove(0, 0, 'S'); // Blue
        game.makeMove(1, 0, 'X'); // Red
        game.makeMove(0, 2, 'S'); // Blue
        game.makeMove(1, 2, 'X'); // Red
        game.makeMove(0, 1, 'O'); // Blue → 1 SOS

        // Red scores diagonal SOS: (2,0)-(1,1)-(0,2)
        game.makeMove(2, 0, 'S'); // Red
        game.makeMove(2, 2, 'X'); // Blue — blocks second diagonal
        game.makeMove(1, 1, 'O'); // Red → 1 SOS

        // Block vertical SOS
        game.makeMove(2, 1, 'X'); // Blue

        assertTrue(game.isGameOver());
        assertEquals(1, game.getBlueScore());
        assertEquals(1, game.getRedScore());
        assertEquals("Draw", game.getWinner());
    }

    @Test
    public void testDoubleDiagonalSOSFromCenterO() {
        GeneralGame game = new GeneralGame(3);

        // Set up diagonals
        game.makeMove(0, 0, 'S'); // Blue
        game.makeMove(0, 1, 'X'); // Red
        game.makeMove(0, 2, 'S'); // Blue
        game.makeMove(1, 0, 'X'); // Red
        game.makeMove(2, 0, 'S'); // Blue
        game.makeMove(1, 2, 'X'); // Red
        game.makeMove(2, 2, 'S'); // Blue

        // Red places 'O' at center to complete both diagonals
        game.makeMove(1, 1, 'O'); // Red → should score 2 SOS

        assertEquals(2, game.getRedScore());
    }
}