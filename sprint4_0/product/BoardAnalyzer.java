package sprint4_0.product;

import java.util.ArrayList;
import java.util.List;

public class BoardAnalyzer {

    public static List<ScoredSequence> checkSOS(Board board, int row, int col, String player) {
        List<ScoredSequence> sequences = new ArrayList<>();
        char[][] grid = board.getGrid();
        int size = board.getSize();

        int[][] directions = {
            {0, -1}, {0, 1},       // horizontal
            {-1, 0}, {1, 0},       // vertical
            {-1, -1}, {1, 1},      // diagonal \
            {-1, 1}, {1, -1}       // diagonal /
        };

        for (int i = 0; i < directions.length; i += 2) {
            int[] dir1 = directions[i];
            int[] dir2 = directions[i + 1];

            int r1 = row + dir1[0];
            int c1 = col + dir1[1];
            int r2 = row + dir2[0];
            int c2 = col + dir2[1];

            if (isInBounds(r1, c1, size) && isInBounds(r2, c2, size)) {
                if (grid[r1][c1] == 'S' && grid[row][col] == 'O' && grid[r2][c2] == 'S') {
                    sequences.add(new ScoredSequence(r1, c1, row, col, r2, c2, player));
                }
            }
        }

        return sequences;
    }

    private static boolean isInBounds(int row, int col, int size) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }
}