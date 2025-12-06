package sprint5_0.test;

import org.junit.Test;
import sprint4_0.product.*;

import static org.junit.Assert.*;

public class TestGameSetup {

    @Test
    public void testLegalBoardSizes() {
        for (int size = 3; size <= 10; size++) {
            SOSGame game = new SimpleGame(size);
            assertEquals(size, game.getBoard().getSize());
        }
    }

    @Test
    public void testIllegalBoardSizes() {
        int[] illegalSizes = {0, 1, 2, -5};

        for (int size : illegalSizes) {
            try {
                new SimpleGame(size);
                fail("Illegal size " + size + " should not be accepted.");
            } catch (IllegalArgumentException e) {
                // Expected
            }
        }
    }
}