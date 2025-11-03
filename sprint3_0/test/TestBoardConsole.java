package sprint3_0.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sprint3_0.product.Board;
import sprint3_0.product.Console;

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
    public void testEmptyBoardDisplaysCorrectly() {
        console.printBoard();
        String output = outputStream.toString();
        assertTrue(output.contains("0 1 2"));
        assertTrue(output.contains(". . ."));
    }

    @Test
    public void testPrintMoveDisplaysCorrectPlayerAndLetter() {
        board.makeMove(1, 1, 'S', "Blue");
        console.printMove(1, 1);
        String output = outputStream.toString().trim();
        assertEquals("Player Blue placed S at (1, 1)", output);
    }

    @Test
    public void testPrintWinnerDisplaysCorrectMessage() {
        console.printWinner("Red");
        assertTrue(outputStream.toString().contains("Winner: Red"));

        outputStream.reset();
        console.printWinner("Draw");
        assertTrue(outputStream.toString().contains("The game ended in a draw."));
    }
}