package sprint4_0.test;

import org.junit.Test;
import sprint4_0.product.*;

import static org.junit.Assert.*;

public class TestComputerPlayer {

    @Test
    public void testComputerCanMakeValidMove() {
        SOSGame game = new SimpleGame(3);
        int[] move = game.getAvailableMoves().get(0);
        boolean success = game.makeMove(move[0], move[1], 'S');
        assertTrue(success);
    }

    @Test
    public void testComputerMoveUpdatesBoard() {
        SOSGame game = new SimpleGame(3);
        int[] move = game.getAvailableMoves().get(0);
        game.makeMove(move[0], move[1], 'O');
        assertEquals('O', game.getBoard().getCell(move[0], move[1]));
    }

    @Test
    public void testGeneralGameScoresSingleSOS() {
        GeneralGame game = new GeneralGame(3);
        game.makeMove(0, 0, 'S'); // Blue
        game.makeMove(1, 0, 'X'); // Red
        game.makeMove(0, 2, 'S'); // Blue
        game.makeMove(1, 2, 'X'); // Red
        game.makeMove(2, 0, 'X'); // Blue
        game.makeMove(2, 1, 'X'); // Red
        game.makeMove(2, 2, 'X'); // Blue
        game.makeMove(1, 1, 'X'); // Red
        game.makeMove(0, 1, 'O'); // Blue → triggers 1 SOS

        assertEquals(1, game.getBlueScore());
    }

    @Test
    public void testGeneralGameScoresMultipleSOS() {
        GeneralGame game = new GeneralGame(3);

        // Horizontal SOS: (0,0)-(0,1)-(0,2)
        game.makeMove(0, 0, 'S'); // Blue
        game.makeMove(1, 0, 'S'); // Red
        game.makeMove(0, 2, 'S'); // Blue
        game.makeMove(1, 2, 'S'); // Red

        // Diagonal SOS: (2,0)-(1,1)-(0,2)
        game.makeMove(2, 0, 'S'); // Blue
        game.makeMove(2, 2, 'S'); // Red

        // Center 'O' triggers 3 SOS: horizontal, diagonal \, diagonal /
        game.makeMove(1, 1, 'O'); // Blue

        assertEquals(3, game.getBlueScore());
    }

    @Test
    public void testComputerMoveDoesNotOverwrite() {
        SOSGame game = new SimpleGame(3);
        game.makeMove(0, 0, 'S');
        game.makeMove(1, 1, 'O');
        boolean success = game.makeMove(0, 0, 'O');
        assertFalse(success);
    }

    @Test
    public void testComputerCanFinishGame() {
        SOSGame game = new SimpleGame(3);
        char[] letters = {'S', 'O'};
        int turn = 0;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (!(row == 2 && col == 2)) {
                    game.makeMove(row, col, letters[turn % 2]);
                    turn++;
                }
            }
        }

        assertFalse(game.isGameOver());
        game.makeMove(2, 2, 'O');
        assertTrue(game.isGameOver());
    }

    @Test
    public void testRandomComputerPlayerMakesMove() {
        SOSGame game = new SimpleGame(3);
        ComputerPlayer ai = new RandomComputerPlayer("Blue");
        ai.makeMove(game);
        int[] lastMove = game.getLastMove();
        assertNotNull(lastMove);
        assertTrue(game.getBoard().getCell(lastMove[0], lastMove[1]) != '\0');
    }
}