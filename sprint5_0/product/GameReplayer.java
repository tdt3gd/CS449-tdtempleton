public class GameReplayer {
    private final GUI gui;
    private final GameRecorder recorder;

    public GameReplayer(GUI gui, GameRecorder recorder) {
        this.gui = gui;
        this.recorder = recorder;
    }

    public void replay() {
        List<String[]> moves = recorder.loadMoves();
        for (String[] move : moves) {
            String player = move[0];
            int row = Integer.parseInt(move[1]);
            int col = Integer.parseInt(move[2]);
            char letter = move[3].charAt(0);

            gui.getGame().makeMove(row, col, letter);
            gui.drawNewBoard();

            try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        }
    }
}