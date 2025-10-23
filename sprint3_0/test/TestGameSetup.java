package sprint3_0.test;

import org.junit.Before;
import org.junit.Test;
import sprint2_0.product.Board;
import sprint2_0.product.GameController;

import static org.junit.Assert.*;

public class TestGameSetup {

    private GameController controller;

    @Before
    public void setUp() {
        controller = new GameController(5); // default valid size
    }

    // AC 1.1: Valid board sizes
    @Test
    public void testValidBoardSizes() {
        for (int size = 3; size <= 8; size++) {
            GameController c = new GameController(size);
            assertEquals(size, c.getBoard().getSize());
        }
    }

    // AC 1.2: Illegal board sizes (should be handled gracefully)
    @Test
    public void testIllegalBoardSizes() {
        int[] illegalSizes = {0, 2, 9, 100};
        for (int size : illegalSizes) {
            try {
                new GameController(size);
                fail("Illegal size " + size + " should not be accepted.");
            } catch (IllegalArgumentException e) {
                // Expected behavior
                assertTrue(true);
            }
        }
    }

    // AC 2.1: Select general mode
    @Test
    public void testSelectGeneralMode() {
        controller.setGameMode(GameController.GameMode.GENERAL);
        assertEquals(GameController.GameMode.GENERAL, controller.getGameMode());
    }

    // AC 2.2: Select simple mode
    @Test
    public void testSelectSimpleMode() {
        controller.setGameMode(GameController.GameMode.SIMPLE);
        assertEquals(GameController.GameMode.SIMPLE, controller.getGameMode());
    }

    // AC 3.1: Start game with selected size and mode
    @Test
    public void testStartGameWithSizeAndMode() {
        GameController c = new GameController(6);
        c.setGameMode(GameController.GameMode.GENERAL);
        assertEquals(6, c.getBoard().getSize());
        assertEquals(GameController.GameMode.GENERAL, c.getGameMode());
    }
}