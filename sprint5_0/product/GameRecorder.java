package sprint5_0.product;

import java.io.*;
import java.util.*;

public class GameRecorder {
    private final File file;

    public GameRecorder(String filename) {
        this.file = new File(filename);
    }

    public void recordMove(String player, int row, int col, char letter) {
        try (FileWriter fw = new FileWriter(file, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(player + "," + row + "," + col + "," + letter);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String[]> loadMoves() {
        List<String[]> moves = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                moves.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return moves;
    }
}