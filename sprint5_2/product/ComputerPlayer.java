package sprint5_2.product;

public abstract class ComputerPlayer {
    protected String player;

    public ComputerPlayer(String player) {
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }

    public abstract void makeMove(SOSGame game);
}