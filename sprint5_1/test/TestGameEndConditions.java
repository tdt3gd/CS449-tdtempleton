package sprint5_1.test;

import org.junit.Test;
import sprint4_0.product.*;

import static org.junit.Assert.*;

public class TestGameEndConditions {

    @Test
    public void testSimpleGameEndsOnFirstSOS_Horizontal() {
        SimpleGame game = new SimpleGame(3);
        game.makeMove(0, 0, 'S'); // Blue
        game.makeMove(1, 0, 'S'); // Red
        game.makeMove(0, 2, 'S'); // Blue
        game.makeMove(1, 2, 'O'); // Red
        game.makeMove(0, 1, 'O'); // Blue → triggers SOS

        assertTrue(game.isGameOver());
        assertEquals("Blue", game.getWinner());
        assertEquals(1, game.getScoredSequences().size());
    }

    @Test
    public void testGeneralGameEndsAfterBoardFull_WinnerByScore() {
        GeneralGame game = new GeneralGame(3);

        game.makeMove(0, 0, 'S');
        game.makeMove(1, 0, 'S');
        game.makeMove(0, 2, 'S');
        game.makeMove(1, 2, 'O');
        game.makeMove(0, 1, 'O'); // Blue → 1 SOS

        game.makeMove(2, 0, 'O');
        game.makeMove(2, 1, 'S');
        game.makeMove(1, 1, 'S');
        game.makeMove(2, 2, 'O');

        assertTrue(game.isGameOver());
        assertEquals("Blue", game.getWinner());
        assertEquals(1, game.getBlueScore());
        assertEquals(0, game.getRedScore());
    }

    @Test
    public void testGeneralGameEndsInDrawWhenScoresEqual() {
        GeneralGame game = new GeneralGame(3);

        game.makeMove(0, 0, 'S'); // Blue
        game.makeMove(0, 1, 'O'); // Red
        game.makeMove(0, 2, 'S'); // Blue → Blue scores 1 (horizontal)
        game.makeMove(1, 0, 'S'); // Red
        game.makeMove(1, 2, 'S'); // Blue
        game.makeMove(1, 1, 'O'); // Red → Red scores 1 (horizontal)
        game.makeMove(2, 0, 'S'); // Blue
        game.makeMove(2, 1, 'O'); // Red
        game.makeMove(2, 2, 'S'); // Blue → Blue scores 1 (horizontal)
        game.makeMove(1, 2, 'S'); // Red → Red scores 1 (vertical)

        assertTrue(game.isGameOver());
        assertEquals(2, game.getBlueScore());
        assertEquals(2, game.getRedScore());
        assertEquals("Draw", game.getWinner());
    }


    @Test
    public void testDoubleDiagonalSOSFromCenterO() {
        GeneralGame game = new GeneralGame(3);

        game.makeMove(0, 0, 'S'); // Blue
        game.makeMove(0, 1, 'S'); // Red
        game.makeMove(0, 2, 'S'); // Blue
        game.makeMove(1, 0, 'S'); // Red
        game.makeMove(2, 0, 'S'); // Blue
        game.makeMove(1, 2, 'S'); // Red
        game.makeMove(2, 2, 'S'); // Blue

        game.makeMove(1, 1, 'O'); // Red → triggers 3 SOS

        assertEquals(3, game.getRedScore());
    }
}