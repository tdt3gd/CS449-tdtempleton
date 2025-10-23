package sprint3_0.test;

import org.junit.Before;
import org.junit.Test;
import sprint2_0.product.Board;
import sprint2_0.product.GameController;

import static org.junit.Assert.*;

/**
 * Unit tests for the SOS Game - covering:
 * - 4.1 Successful move in simple game
 * - 4.2 Unsuccessful move in simple game
 * - 6.1 Successful move in general game
 * - 6.2 Unsuccessful move in general game
 */
public class TestGameMoves {

    private GameController controller;

    @Before
    public void setUp() {
        controller = new GameController(5); // Default board size
    }

    // -------------------------------
    // User Story 4 - Simple Game Moves
    // -------------------------------

    // 4.1 AC 4.1 - Successful move in a simple game
    @Test
    public void testSuccessfulMoveInSimpleGame() {
        controller.setGameMode(GameController.GameMode.SIMPLE);
        boolean moveMade = controller.makeMove(2, 2, 'S');
        assertTrue("Move should be successful on an empty cell", moveMade);

        char cellValue = controller.getBoard().getCell(2, 2);
        assertEquals("Cell should contain 'S'", 'S', cellValue);
    }

    // 4.2 AC 4.2 - Unsuccessful move in a simple game
    @Test
    public void testUnsuccessfulMoveInOccupiedCell() {
        controller.setGameMode(GameController.GameMode.SIMPLE);
        controller.makeMove(1, 1, 'O'); // First move
        boolean moveMade = controller.makeMove(1, 1, 'S'); // Attempt same cell

        assertFalse("Move should be unsuccessful on an occupied cell", moveMade);

        char cellValue = controller.getBoard().getCell(1, 1);
        assertEquals("Cell should still contain the original value 'O'", 'O', cellValue);
    }

    // -------------------------------
    // User Story 6 - General Game Moves
    // -------------------------------

    // 6.1 AC 6.1 - Successful move in a general game
    @Test
    public void testSuccessfulMoveInGeneralGame() {
        controller.setGameMode(GameController.GameMode.GENERAL);
        boolean moveMade = controller.makeMove(3, 3, 'O');
        assertTrue("Move should be successful in general game mode on empty cell", moveMade);

        char cellValue = controller.getBoard().getCell(3, 3);
        assertEquals("Cell should contain 'O'", 'O', cellValue);
    }

    // 6.2 AC 6.2 - Unsuccessful move in a general game
    @Test
    public void testUnsuccessfulMoveInGeneralGame() {
        controller.setGameMode(GameController.GameMode.GENERAL);
        controller.makeMove(0, 0, 'S'); // First move
        boolean moveMade = controller.makeMove(0, 0, 'O'); // Attempt on occupied cell

        assertFalse("Move should be unsuccessful on occupied cell in general game", moveMade);

        char cellValue = controller.getBoard().getCell(0, 0);
        assertEquals("Cell should still contain the original value 'S'", 'S', cellValue);
    }
}
