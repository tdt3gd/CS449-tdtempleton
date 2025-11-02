package sprint3_0.product;

public class ScoredSequence {
    public final int startRow;
    public final int startCol;
    public final int endRow;
    public final int endCol;
    public final String player;

    public ScoredSequence(int startRow, int startCol, int endRow, int endCol, String player) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.player = player;
    }
}