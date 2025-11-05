package sprint4_0.test;

import org.junit.Test;
import sprint3_0.product.SimpleGame;
import sprint3_0.product.GeneralGame;

import static org.junit.Assert.*;

public class TestGameSetup {

    @Test
    public void testValidBoardSizes() {
        for (int size = 3; size <= 8; size++) {
            SimpleGame simple = new SimpleGame(size);
            GeneralGame general = new GeneralGame(size);
            assertEquals(size, simple.getBoard().getSize());
            assertEquals(size, general.getBoard().getSize());
        }
    }

    @Test
    public void testIllegalBoardSizes() {
        int[] illegalSizes = {0, 2, 9, 100};
        for (int size : illegalSizes) {
            try {
                new SimpleGame(size);
                fail("Illegal size " + size + " should not be accepted.");
            } catch (IllegalArgumentException e) {
                assertTrue(true);
            }

            try {
                new GeneralGame(size);
                fail("Illegal size " + size + " should not be accepted.");
            } catch (IllegalArgumentException e) {
                assertTrue(true);
            }
        }
    }
}