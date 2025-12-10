package sprint4_2.product;

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

    // --- Core game and console ---
    private SOSGame game;
    private Console console;

    // --- UI controls ---
    private ComboBox<Integer> boardSizeSelector = new ComboBox<>();
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
    private Button newGameButton = new Button("New Game");

    // --- Board and drawing ---
    private int gridSize = 8;
    private Square[][] squares;
    private GridPane boardPane = new GridPane();
    private Pane lineLayer = new Pane();

    // --- Containers used across helpers ---
    private VBox controlBox = new VBox(10);
    private StackPane boardStack = new StackPane();
    private HBox boardRow = new HBox(20);
    private TextArea consoleOutput = new TextArea();

    // --- AI registry ---
    private Map<String, ComputerPlayer> computerPlayers = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        initGame();
        initControls();
        initBoardUI();
        initPlayerBoxes();
        initBottomUI(primaryStage);
    }

    // --- Initialize game and console ---
    private void initGame() {
        game = new SimpleGame(gridSize); // default mode
        gridSize = game.getBoard().getSize();
        console = new Console(game.getBoard(), msg -> consoleOutput.appendText(msg + "\n"));
        squares = new Square[gridSize][gridSize];
        this.drawNewBoard();
    }

    // --- Build top row controls ---
    private void initControls() {
        boardSizeSelector.getItems().addAll(3, 4, 5, 6, 7, 8);
        boardSizeSelector.setValue(gridSize);
        boardSizeSelector.setOnAction(e -> {
            gridSize = boardSizeSelector.getValue();
            this.drawNewBoard();
        });

        Label titleLabel = new Label("SOS");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        radioBtnGroup = new ToggleGroup();
        radioButton1.setToggleGroup(radioBtnGroup);
        radioButton2.setToggleGroup(radioBtnGroup);
        radioButton1.setSelected(true);

        radioButton1.setOnAction(e -> {
            game = new SimpleGame(gridSize);
            gridSize = game.getBoard().getSize();
            console = new Console(game.getBoard(), msg -> consoleOutput.appendText(msg + "\n"));
            this.drawNewBoard();
        });

        radioButton2.setOnAction(e -> {
            game = new GeneralGame(gridSize);
            gridSize = game.getBoard().getSize();
            console = new Console(game.getBoard(), msg -> consoleOutput.appendText(msg + "\n"));
            this.drawNewBoard();
        });

        HBox boardSizeBox = new HBox(5, new Label("Board size:"), boardSizeSelector);
        boardSizeBox.setAlignment(Pos.CENTER_RIGHT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox topRow = new HBox(20, titleLabel, radioButton1, radioButton2, spacer, boardSizeBox);
        topRow.setAlignment(Pos.CENTER_LEFT);
        topRow.setPadding(new Insets(10));

        Label speedLabel = new Label("AI Speed (ms):");
        speedSlider.setShowTickLabels(true);
        speedSlider.setShowTickMarks(true);

        HBox speedBox = new HBox(10, speedLabel, speedSlider);
        speedBox.setAlignment(Pos.CENTER);

        controlBox.getChildren().addAll(topRow, speedBox);
    }

    // --- Build board UI ---
    private void initBoardUI() {
        boardPane.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        lineLayer.setPickOnBounds(false);

        boardStack = new StackPane(boardPane, lineLayer);
        boardStack.setPrefSize(500, 500);
    }

    // --- Build player panels ---
    private void initPlayerBoxes() {
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

        boardRow = new HBox(20, bluePlayerBox, boardStack, redPlayerBox);
        boardRow.setAlignment(Pos.CENTER);
        boardRow.setPadding(new Insets(10));
    }

    // --- Build bottom row ---
    private void initBottomUI(Stage primaryStage) {
        newGameButton.setOnAction(e -> {
            game = radioButton1.isSelected() ? new SimpleGame(gridSize) : new GeneralGame(gridSize);
            console = new Console(game.getBoard(), msg -> consoleOutput.appendText(msg + "\n"));
            gameStatus.setText("Blue's Turn");
            consoleOutput.clear();
            blueScoreLabel.setText("Score: 0");
            redScoreLabel.setText("Score: 0");
            squares = new Square[gridSize][gridSize];
            computerPlayers.clear();
            if (bluePlayerType.getValue().equals("Computer")) computerPlayers.put("Blue", new RandomComputerPlayer("Blue"));
            if (redPlayerType.getValue().equals("Computer")) computerPlayers.put("Red", new RandomComputerPlayer("Red"));
            this.drawNewBoard();
            if (isCurrentPlayerComputer()) {
                PauseTransition aiPause = new PauseTransition(Duration.millis(speedSlider.getValue()));
                aiPause.setOnFinished(ev -> makeComputerMove());
                aiPause.play();
            }
        });

        BorderPane bottomRow = new BorderPane();
        bottomRow.setCenter(new HBox(gameStatus));
        bottomRow.setRight(new HBox(newGameButton));

        consoleOutput.setEditable(false);
        consoleOutput.setPrefHeight(120);
        consoleOutput.setWrapText(true);

        VBox bottomBox = new VBox(10, bottomRow, consoleOutput);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(controlBox);
        borderPane.setCenter(boardRow);
        borderPane.setBottom(bottomBox);

        Scene scene = new Scene(borderPane, 800, 650);
        primaryStage.setTitle("SOS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // --- Drawing orchestration ---
    public void drawNewBoard() {
        squares = new Square[gridSize][gridSize];
        this.drawBoard();
    }

    public void drawBoard() {
        clearBoardLayers();
        drawSquares();
        updateScores();
        drawSequences();
    }

    private void clearBoardLayers() {
        boardPane.getChildren().clear();
        lineLayer.getChildren().clear();
    }

    private void drawSquares() {
        for (int r = 0; r < gridSize; r++) {
            for (int c = 0; c < gridSize; c++) {
                Square square = new Square(r, c);
                squares[r][c] = square;
                boardPane.add(square, c, r);
                char cell = game.getBoard().getCell(r, c);
                if (cell == 'S') square.drawCross();
                else if (cell == 'O') square.drawNought();
            }
        }
    }

    private void updateScores() {
        if (game instanceof GeneralGame) {
            blueScoreLabel.setText("Score: " + ((GeneralGame) game).getBlueScore());
            redScoreLabel.setText("Score: " + ((GeneralGame) game).getRedScore());
        }
        if (game instanceof SimpleGame && game.isGameOver() && !game.getScoredSequences().isEmpty()) {
            String winner = game.getWinner();
            if ("Blue".equals(winner)) {
                blueScoreLabel.setText("Score: 1");
            } else if ("Red".equals(winner)) {
                redScoreLabel.setText("Score: 1");
            }
        }
    }

    private void drawSequences() {
        PauseTransition pause = new PauseTransition(Duration.millis(50));
        pause.setOnFinished(e -> {
            for (ScoredSequence seq : game.getScoredSequences()) {
                if (isInBounds(seq.startRow, seq.startCol) && isInBounds(seq.endRow, seq.endCol)) {
                    drawLineBetweenSquares(seq.startRow, seq.startCol, seq.endRow, seq.endCol, seq.player);
                }
            }
        });
        pause.play();
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

    // --- Square class ---
    private class Square extends StackPane {
        private final int row, col;

        public Square(int row, int col) {
            this.row = row;
            this.col = col;
            setPrefSize(60, 60);
            setStyle("-fx-border-color: black; -fx-background-color: white;");
            setOnMouseClicked(e -> handleClick());
        }

        private void handleClick() {
            if (game.isGameOver()) return;
            if (isCurrentPlayerComputer()) return;

            char letter = getSelectedLetterForCurrentPlayer();
            String player = game.getCurrentPlayer();
            if (game.makeMove(row, col, letter)) {
                drawBoard();
                console.printMove(player, row, col, letter);
                if (game.isGameOver()) {
                    gameStatus.setText("Game Over: " + game.getWinner());
                    console.printWinner(game.getWinner());
                } else {
                    gameStatus.setText(game.getCurrentPlayer() + "'s Turn");
                    if (isCurrentPlayerComputer()) {
                        PauseTransition aiPause = new PauseTransition(Duration.millis(speedSlider.getValue()));
                        aiPause.setOnFinished(ev -> makeComputerMove());
                        aiPause.play();
                    }
                }
            }
        }

        public void drawCross() {
            Label label = new Label("S");
            label.setStyle("-fx-font-size: 24px; -fx-text-fill: black;");
            getChildren().add(label);
        }

        public void drawNought() {
            Label label = new Label("O");
            label.setStyle("-fx-font-size: 24px; -fx-text-fill: black;");
            getChildren().add(label);
        }
    }

    // --- AI helpers ---
    private char getSelectedLetterForCurrentPlayer() {
        String player = game.getCurrentPlayer();
        if (player.equals("Blue")) {
            return blueS.isSelected() ? 'S' : 'O';
        } else {
            return redS.isSelected() ? 'S' : 'O';
        }
    }

    private boolean isCurrentPlayerComputer() {
        return computerPlayers.containsKey(game.getCurrentPlayer());
    }

    private void makeComputerMove() {
        ComputerPlayer ai = computerPlayers.get(game.getCurrentPlayer());
        if (ai == null) return;

        ai.makeMove(game);
        drawBoard();

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
            if (isCurrentPlayerComputer()) {
                PauseTransition aiPause = new PauseTransition(Duration.millis(speedSlider.getValue()));
                aiPause.setOnFinished(ev -> makeComputerMove());
                aiPause.play();
            }
        }
    }

    // --- Utility methods ---
    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < gridSize && col >= 0 && col < gridSize;
    }

    // --- Entry point ---
    public static void main(String[] args) {
        launch(args);
    }
}