package sprint5_0.product;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

public class GUI extends Application {

    private SOSGame game;
    private Console console;

    private ComboBox<Integer> boardSizeSelector = new ComboBox<>();
    private Square[][] squares;
    private Label gameStatus = new Label("Blue's Turn");

    private RadioButton radioButton1 = new RadioButton("Simple game");
    private RadioButton radioButton2 = new RadioButton("General game");
    private ToggleGroup radioBtnGroup;

    private ToggleGroup bluePlayerGroup = new ToggleGroup();
    private RadioButton blueS = new RadioButton("S");
    private RadioButton blueO = new RadioButton("O");

    private ToggleGroup redPlayerGroup = new ToggleGroup();
    private RadioButton redS = new RadioButton("S");
    private RadioButton redO = new RadioButton("O");

    private ComboBox<String> bluePlayerType = new ComboBox<>();
    private ComboBox<String> redPlayerType = new ComboBox<>();

    private Label blueScoreLabel = new Label("Score: 0");
    private Label redScoreLabel = new Label("Score: 0");

    private Slider speedSlider = new Slider(100, 1000, 300); // AI speed in ms

    private int gridSize = 8;
    private GridPane boardPane = new GridPane();
    private Pane lineLayer = new Pane();
    private TextArea consoleOutput = new TextArea();
    private Button newGameButton = new Button("New Game");

    private Map<String, ComputerPlayer> computerPlayers = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        initGame(true); // start with SimpleGame
        buildUI(primaryStage);
    }

    // -------------------- Initialization --------------------

    private void initGame(boolean isSimple) {
        game = isSimple ? new SimpleGame(gridSize) : new GeneralGame(gridSize);
        gridSize = game.getBoard().getSize();
        console = new Console(game.getBoard(), msg -> consoleOutput.appendText(msg + "\n"));
        squares = new Square[gridSize][gridSize];
        drawNewBoard();
    }
    // -------------------- UI Construction --------------------

    private void buildUI(Stage primaryStage) {
        HBox topRow = buildTopRow();
        VBox controlBox = buildControlBox(topRow);
        HBox boardRow = buildBoardRow();
        VBox bottomBox = buildBottomBox();

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(controlBox);
        borderPane.setCenter(boardRow);
        borderPane.setBottom(bottomBox);

        Scene scene = new Scene(borderPane, 800, 650);
        primaryStage.setTitle("SOS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox buildTopRow() {
        HBox topRow = new HBox(20);
        topRow.setAlignment(Pos.CENTER_LEFT);
        topRow.setPadding(new Insets(10));

        Label titleLabel = new Label("SOS");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        radioBtnGroup = new ToggleGroup();
        radioButton1.setToggleGroup(radioBtnGroup);
        radioButton2.setToggleGroup(radioBtnGroup);
        radioButton1.setSelected(true);

        radioButton1.setOnAction(e -> initGame(true));
        radioButton2.setOnAction(e -> initGame(false));

        boardSizeSelector.getItems().addAll(3, 4, 5, 6, 7, 8);
        boardSizeSelector.setValue(gridSize);
        boardSizeSelector.setOnAction(e -> {
            gridSize = boardSizeSelector.getValue();
            drawNewBoard();
        });

        HBox boardSizeBox = new HBox(5, new Label("Board size:"), boardSizeSelector);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topRow.getChildren().addAll(titleLabel, radioButton1, radioButton2, spacer, boardSizeBox);
        return topRow;
    }
    private VBox buildControlBox(HBox topRow) {
        VBox controlBox = new VBox(10);
        controlBox.setAlignment(Pos.CENTER);
        controlBox.setPadding(new Insets(10));

        Label speedLabel = new Label("AI Speed (ms):");
        speedSlider.setShowTickLabels(true);
        speedSlider.setShowTickMarks(true);
        speedSlider.setMajorTickUnit(300);
        speedSlider.setMinorTickCount(2);
        speedSlider.setBlockIncrement(100);

        HBox speedBox = new HBox(10, speedLabel, speedSlider);
        speedBox.setAlignment(Pos.CENTER);

        controlBox.getChildren().addAll(topRow, speedBox);
        return controlBox;
    }

    private HBox buildBoardRow() {
        VBox bluePlayerBox = new VBox(10, new Label("Blue Player"), blueS, blueO, bluePlayerType, blueScoreLabel);
        bluePlayerBox.setAlignment(Pos.CENTER);
        blueS.setToggleGroup(bluePlayerGroup);
        blueO.setToggleGroup(bluePlayerGroup);
        blueS.setSelected(true);
        bluePlayerType.getItems().addAll("Human", "Computer");
        bluePlayerType.setValue("Human");

        VBox redPlayerBox = new VBox(10, new Label("Red Player"), redS, redO, redPlayerType, redScoreLabel);
        redPlayerBox.setAlignment(Pos.CENTER);
        redS.setToggleGroup(redPlayerGroup);
        redO.setToggleGroup(redPlayerGroup);
        redS.setSelected(true);
        redPlayerType.getItems().addAll("Human", "Computer");
        redPlayerType.setValue("Human");

        boardPane.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        StackPane boardStack = new StackPane(boardPane, lineLayer);
        boardStack.setPrefSize(500, 500);

        HBox boardRow = new HBox(20, bluePlayerBox, boardStack, redPlayerBox);
        boardRow.setAlignment(Pos.CENTER);
        boardRow.setPadding(new Insets(10));
        return boardRow;
    }
    private VBox buildBottomBox() {
        newGameButton.setOnAction(e -> resetGame());

        BorderPane bottomRow = new BorderPane();
        bottomRow.setPadding(new Insets(10));
        bottomRow.setCenter(new HBox(gameStatus));
        bottomRow.setRight(new HBox(newGameButton));

        consoleOutput.setEditable(false);
        consoleOutput.setPrefHeight(120);
        consoleOutput.setWrapText(true);

        return new VBox(10, bottomRow, consoleOutput);
    }

    // -------------------- Game Reset --------------------

    private void resetGame() {
        game = radioButton1.isSelected() ? new SimpleGame(gridSize) : new GeneralGame(gridSize);
        console = new Console(game.getBoard(), msg -> consoleOutput.appendText(msg + "\n"));
        gameStatus.setText("Blue's Turn");
        consoleOutput.clear();
        blueScoreLabel.setText("Score: 0");
        redScoreLabel.setText("Score: 0");
        squares = new Square[gridSize][gridSize];

        computerPlayers.clear();
        if (bluePlayerType.getValue().equals("Computer")) {
            computerPlayers.put("Blue", new RandomComputerPlayer("Blue"));
        }
        if (redPlayerType.getValue().equals("Computer")) {
            computerPlayers.put("Red", new RandomComputerPlayer("Red"));
        }

        drawNewBoard();
        if (isCurrentPlayerComputer()) triggerComputerMove();
    }
    // -------------------- Board Rendering --------------------

    public void drawNewBoard() {
        resetSquares();
        boardPane.getChildren().clear();
        lineLayer.getChildren().clear();
        populateBoard();
        updateScores();
        renderScoredSequences();
    }

    public void resetSquares() {
        squares = new Square[gridSize][gridSize];
    }

    private void populateBoard() {
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Square square = createSquare(row, col);
                char cell = game.getBoard().getCell(row, col);
                renderCell(square, cell);
            }
        }
    }

    public Square createSquare(int row, int col) {
        Square square = new Square(row, col, this); // pass GUI reference
        squares[row][col] = square;
        boardPane.add(square, col, row);
        return square;
    }

    public void renderCell(Square square, char cell) {
        if (cell == 'S') square.drawCross();
        else if (cell == 'O') square.drawNought();
    }

    public void updateScores() {
        if (game instanceof GeneralGame) {
            blueScoreLabel.setText("Score: " + ((GeneralGame) game).getBlueScore());
            redScoreLabel.setText("Score: " + ((GeneralGame) game).getRedScore());
        } else if (game instanceof SimpleGame && game.isGameOver() && !game.getScoredSequences().isEmpty()) {
            String winner = game.getWinner();
            if ("Blue".equals(winner)) blueScoreLabel.setText("Score: 1");
            else if ("Red".equals(winner)) redScoreLabel.setText("Score: 1");
        }
    }

    public void renderScoredSequences() {
        for (ScoredSequence seq : game.getScoredSequences()) {
            if (isInBounds(seq.startRow, seq.startCol) && isInBounds(seq.endRow, seq.endCol)) {
                drawLineBetweenSquares(seq.startRow, seq.startCol, seq.endRow, seq.endCol, seq.player);
            }
        }
    }

    private void drawLineBetweenSquares(int r1, int c1, int r2, int c2, String player) {
        Square start = squares[r1][c1];
        Square end = squares[r2][c2];

        Bounds startBounds = start.localToParent(start.getBoundsInLocal());
        Bounds endBounds = end.localToParent(end.getBoundsInLocal());

        double x1 = startBounds.getMinX() + startBounds.getWidth() / 2;
        double y1 = startBounds.getMinY() + startBounds.getHeight() / 2;
        double x2 = endBounds.getMinX() + endBounds.getWidth() / 2;
        double y2 = endBounds.getMinY() + endBounds.getHeight() / 2;

        Line line = new Line(x1, y1, x2, y2);
        line.setStroke(player.equals("Blue") ? Color.BLUE : Color.RED);
        line.setStrokeWidth(4);
        lineLayer.getChildren().add(line);
    }
    // -------------------- Helpers --------------------

    public char getSelectedLetterForCurrentPlayer() {
        String player = game.getCurrentPlayer();
        if (player.equals("Blue")) {
            return blueS.isSelected() ? 'S' : 'O';
        } else {
            return redS.isSelected() ? 'S' : 'O';
        }
    }

    public boolean isCurrentPlayerComputer() {
        return computerPlayers.containsKey(game.getCurrentPlayer());
    }

    public void triggerComputerMove() {
        PauseTransition aiPause = new PauseTransition(Duration.millis(speedSlider.getValue()));
        aiPause.setOnFinished(ev -> makeComputerMove());
        aiPause.play();
    }

    public void makeComputerMove() {
        ComputerPlayer ai = computerPlayers.get(game.getCurrentPlayer());
        if (ai == null) return;

        ai.makeMove(game);
        drawNewBoard();

        int[] lastMove = game.getLastMove();
        if (lastMove != null) {
            String previousPlayer = game.getCurrentPlayer().equals("Blue") ? "Red" : "Blue";
            char letter = game.getLastLetter();
            console.printMove(previousPlayer, lastMove[0], lastMove[1], letter);
        }

        if (game.isGameOver()) {
            gameStatus.setText("Game Over: " + game.getWinner());
            console.printWinner(game.getWinner());
        } else {
            gameStatus.setText(game.getCurrentPlayer() + "'s Turn");
            if (isCurrentPlayerComputer()) triggerComputerMove();
        }
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < gridSize && col >= 0 && col < gridSize;
    }

    // --- Accessors for other classes ---
    public GridPane getBoardPane() { return boardPane; }
    public Pane getLineLayer() { return lineLayer; }
    public int getGridSize() { return gridSize; }
    public SOSGame getGame() { return game; }
    public Console getConsole() { return console; }
    public Label getGameStatus() { return gameStatus; }
    public Slider getSpeedSlider() { return speedSlider; }
    public Square[][] getSquares() { return squares; }
    public TextArea getConsoleOutput() { return consoleOutput; }
    public void setConsole(Console console) { this.console = console; }
    public Map<String, ComputerPlayer> getComputerPlayers() { return computerPlayers; }

    public static void main(String[] args) {
        launch(args);
    }
}