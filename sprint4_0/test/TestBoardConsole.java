package sprint4_0.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sprint4_0.product.Board;
import sprint4_0.product.Console;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class TestBoardConsole {
    private Board board;
    private ByteArrayOutputStream outputStream;
    private Console console;

    @Before
    public void setUp() {
        board = new Board(3);
        outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        console = new Console(board, printStream::println);
    }

    @After
    public void tearDown() {
        board = null;
        outputStream = null;
        console = null;
    }

    @Test
    public void testPrintMoveDisplaysCorrectPlayerAndLetter() {
        board.setCell(1, 1, 'S');
        console.printMove("Blue", 1, 1, 'S');
        String output = outputStream.toString().trim();
        assertEquals("Blue played 'S' at (1, 1)", output);
    }

    @Test
    public void testPrintWinnerDisplaysCorrectMessage() {
        console.printWinner("Red");
        assertTrue(outputStream.toString().contains("Winner: Red"));

        outputStream.reset();
        console.printWinner("Draw");
        assertTrue(outputStream.toString().contains("The game ended in a draw."));
    }

    @Test
    public void testPrintBoardDisplaysCorrectLayout() {
        board.setCell(0, 0, 'S');
        board.setCell(1, 1, 'O');
        board.setCell(2, 2, 'S');

        console.printBoard();
        String output = outputStream.toString();

        assertTrue(output.contains("0 1 2"));          // Column headers
        assertTrue(output.contains("0 S . ."));        // Row 0
        assertTrue(output.contains("1 . O ."));        // Row 1
        assertTrue(output.contains("2 . . S"));        // Row 2
    }
}