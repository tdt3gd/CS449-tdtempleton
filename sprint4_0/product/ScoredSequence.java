package sprint4_0.product;

public class ScoredSequence {
    public final int startRow;
    public final int startCol;
    public final int midRow;
    public final int midCol;
    public final int endRow;
    public final int endCol;
    public final String player;

    public ScoredSequence(int startRow, int startCol, int midRow, int midCol, int endRow, int endCol, String player) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.midRow = midRow;
        this.midCol = midCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.player = player;
    }

    @Override
    public String toString() {
        return String.format("%s scored SOS from (%d,%d) → (%d,%d) → (%d,%d)",
                player, startRow, startCol, midRow, midCol, endRow, endCol);
    }
}