package sprint5_0.product;

import java.util.List;
import java.util.Random;

public class RandomComputerPlayer extends ComputerPlayer {
    private final Random random = new Random();

    public RandomComputerPlayer(String color) {
        super(color);
    }

    @Override
    public void makeMove(SOSGame game) {
        List<int[]> moves = game.getAvailableMoves();
        if (!moves.isEmpty()) {
            int[] move = moves.get(random.nextInt(moves.size()));
            char letter = random.nextBoolean() ? 'S' : 'O';
            game.makeMove(move[0], move[1], letter);
        }
    }
}