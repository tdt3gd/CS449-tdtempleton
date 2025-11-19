package sprint4_0.product;

import java.util.ArrayList;
import java.util.List;

public class BoardAnalyzer {

    public static List<ScoredSequence> checkSOS(Board board, int row, int col, String player) {
        List<ScoredSequence> sequences = new ArrayList<>();
        char[][] grid = board.getGrid();
        int size = board.getSize();
        char center = grid[row][col];

        int[][] directions = {
            {-1, 0}, {1, 0},     // vertical
            {0, -1}, {0, 1},     // horizontal
            {-1, -1}, {1, 1},    // diagonal \
            {-1, 1}, {1, -1}     // diagonal /
        };

        for (int[] dir : directions) {
            int dr = dir[0], dc = dir[1];

            // Case 1: current cell is the middle 'O'
            int r1 = row - dr, c1 = col - dc;
            int r2 = row + dr, c2 = col + dc;
            if (isInBounds(r1, c1, size) && isInBounds(r2, c2, size)) {
                if (grid[r1][c1] == 'S' && center == 'O' && grid[r2][c2] == 'S') {
                    if (isCanonical(r1, c1, r2, c2)) {
                        sequences.add(new ScoredSequence(r1, c1, row, col, r2, c2, player));
                    }
                }
            }

            // Case 2: current cell is the starting 'S'
            r1 = row + dr;
            c1 = col + dc;
            r2 = row + 2 * dr;
            c2 = col + 2 * dc;
            if (isInBounds(r1, c1, size) && isInBounds(r2, c2, size)) {
                if (center == 'S' && grid[r1][c1] == 'O' && grid[r2][c2] == 'S') {
                    if (isCanonical(row, col, r2, c2)) {
                        sequences.add(new ScoredSequence(row, col, r1, c1, r2, c2, player));
                    }
                }
            }

            // Case 3: current cell is the ending 'S'
            r1 = row - dr;
            c1 = col - dc;
            r2 = row - 2 * dr;
            c2 = col - 2 * dc;
            if (isInBounds(r1, c1, size) && isInBounds(r2, c2, size)) {
                if (grid[r2][c2] == 'S' && grid[r1][c1] == 'O' && center == 'S') {
                    if (isCanonical(r2, c2, row, col)) {
                        sequences.add(new ScoredSequence(r2, c2, r1, c1, row, col, player));
                    }
                }
            }
        }

        return sequences;
    }

    private static boolean isInBounds(int row, int col, int size) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    // Only allow one direction per SOS: start must be "less than" end
    private static boolean isCanonical(int r1, int c1, int r2, int c2) {
        return (r1 < r2) || (r1 == r2 && c1 < c2);
    }
}