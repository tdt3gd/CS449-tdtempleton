package sprint3_0.product;

public class ScoredSequence {
    public final int startRow, startCol, endRow, endCol;
    public final char player;

    public ScoredSequence(int sr, int sc, int er, int ec, char p) {
        startRow = sr;
        startCol = sc;
        endRow = er;
        endCol = ec;
        player = p;
    }
}