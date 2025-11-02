package sprint3_0.product;

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

    private int gridSize = 8;
    private GridPane boardPane = new GridPane();
    private Pane lineLayer = new Pane();
    private CheckBox checkBox1 = new CheckBox("Enable AI");
    private TextArea consoleOutput = new TextArea();

    @Override
    public void start(Stage primaryStage) {
        game = new SimpleGame(gridSize);
        console = new Console(game.getBoard(), msg -> consoleOutput.appendText(msg + "\n"));
        squares = new Square[gridSize][gridSize];
        drawNewBoard();

        HBox topRow = new HBox(20);
        topRow.setAlignment(Pos.CENTER_LEFT);
        topRow.setPadding(new Insets(10));
        topRow.setPrefWidth(Double.MAX_VALUE);

        Label titleLabel = new Label("SOS");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        titleLabel.setMinWidth(50);
        titleLabel.setMaxWidth(100);

        radioBtnGroup = new ToggleGroup();
        radioButton1.setToggleGroup(radioBtnGroup);
        radioButton2.setToggleGroup(radioBtnGroup);
        radioButton1.setMinWidth(100);
        radioButton2.setMinWidth(100);
        radioButton1.setSelected(true);

        radioButton1.setOnAction(e -> {
            game = new SimpleGame(gridSize);
            console = new Console(game.getBoard(), msg -> consoleOutput.appendText(msg + "\n"));
            drawNewBoard();
        });

        radioButton2.setOnAction(e -> {
            game = new GeneralGame(gridSize);
            console = new Console(game.getBoard(), msg -> consoleOutput.appendText(msg + "\n"));
            drawNewBoard();
        });

        boardSizeSelector.getItems().addAll(3, 4, 5, 6, 7, 8);
        boardSizeSelector.setValue(gridSize);
        boardSizeSelector.setPrefWidth(60);
        boardSizeSelector.setOnAction(e -> {
            gridSize = boardSizeSelector.getValue();
            if (radioButton1.isSelected()) {
                game = new SimpleGame(gridSize);
            } else {
                game = new GeneralGame(gridSize);
            }
            console = new Console(game.getBoard(), msg -> consoleOutput.appendText(msg + "\n"));
            drawNewBoard();
        });

        HBox boardSizeBox = new HBox(5);
        boardSizeBox.setAlignment(Pos.CENTER_RIGHT);
        Label boardSizeLabel = new Label("Board size:");
        boardSizeLabel.setMinWidth(80);
        boardSizeBox.getChildren().addAll(boardSizeLabel, boardSizeSelector);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topRow.getChildren().addAll(titleLabel, radioButton1, radioButton2, spacer, boardSizeBox);

        VBox controlBox = new VBox(10);
        controlBox.setAlignment(Pos.CENTER);
        controlBox.setPadding(new Insets(10));
        controlBox.setPrefWidth(Double.MAX_VALUE);
        controlBox.getChildren().addAll(topRow, checkBox1);

        VBox bluePlayerBox = new VBox(10);
        bluePlayerBox.setAlignment(Pos.CENTER);
        bluePlayerBox.setPrefWidth(120);
        Label blueLabel = new Label("Blue Player");
        blueLabel.setMinWidth(100);
        blueS.setToggleGroup(bluePlayerGroup);
        blueO.setToggleGroup(bluePlayerGroup);
        blueS.setSelected(true);
        bluePlayerBox.getChildren().addAll(blueLabel, blueS, blueO);

        VBox redPlayerBox = new VBox(10);
        redPlayerBox.setAlignment(Pos.CENTER);
        redPlayerBox.setPrefWidth(120);
        Label redLabel = new Label("Red Player");
        redLabel.setMinWidth(100);
        redS.setToggleGroup(redPlayerGroup);
        redO.setToggleGroup(redPlayerGroup);
        redS.setSelected(true);
        redPlayerBox.getChildren().addAll(redLabel, redS, redO);

        boardPane.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        boardPane.setPrefWidth(500);
        lineLayer.setPickOnBounds(false);
        StackPane boardStack = new StackPane(boardPane, lineLayer);
        boardStack.setPrefSize(500, 500);
        HBox.setHgrow(boardStack, Priority.ALWAYS);

        HBox boardRow = new HBox(20);
        boardRow.setAlignment(Pos.CENTER);
        boardRow.setPadding(new Insets(10));
        boardRow.getChildren().addAll(bluePlayerBox, boardStack, redPlayerBox);

        HBox statusBox = new HBox(gameStatus);
        statusBox.setAlignment(Pos.CENTER);
        statusBox.setPadding(new Insets(10));

        consoleOutput.setEditable(false);
        consoleOutput.setPrefHeight(120);
        consoleOutput.setWrapText(true);
        consoleOutput.setStyle("-fx-font-family: monospace; -fx-font-size: 12px;");
        VBox bottomBox = new VBox(10, statusBox, consoleOutput);
        bottomBox.setPadding(new Insets(10));

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(controlBox);
        borderPane.setCenter(boardRow);
        borderPane.setBottom(bottomBox);

        Scene scene = new Scene(borderPane, 800, 650);
        primaryStage.setTitle("SOS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void drawNewBoard() {
        squares = new Square[gridSize][gridSize];
        drawBoard();
    }

    public void drawBoard() {
        boardPane.getChildren().clear();
        lineLayer.getChildren().clear();

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Square square = new Square(row, col);
                squares[row][col] = square;
                boardPane.add(square, col, row);

                char cell = game.getBoard().getCell(row, col);
                if (cell == 'S') {
                    square.drawCross();
                } else if (cell == 'O') {
                    square.drawNought();
                }
            }
        }

        PauseTransition pause = new PauseTransition(Duration.millis(50));
        pause.setOnFinished(e -> {
            for (ScoredSequence seq : game.getScoredSequences()) {
                drawLineBetweenSquares(seq.startRow, seq.startCol, seq.endRow, seq.endCol, seq.player);
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

    private class Square extends StackPane {
        private final int row;
        private final int col;

        public Square(int row, int col) {
            this.row = row;
            this.col = col;
            setPrefSize(60, 60);
            setStyle("-fx-border-color: black; -fx-background-color: white;");
            setOnMouseClicked(e -> handleClick());
        }

        private void handleClick() {
            if (game.isGameOver()) return;

            char letter = getSelectedLetterForCurrentPlayer();
            if (game.makeMove(row, col, letter)) {
                drawBoard();
                console.printMove(row, col);
                if (game.isGameOver()) {
                    gameStatus.setText("Game Over: " + game.getWinner());
                    console.printWinner(game.getWinner());
                } else {
                    gameStatus.setText(game.getCurrentPlayer() + "'s Turn");
                }
            }
        }

        private char getSelectedLetterForCurrentPlayer() {
            String player = game.getCurrentPlayer();
            if (player.equals("Blue")) {
                return blueS.isSelected() ? 'S' : 'O';
            } else {
                return redS.isSelected() ? 'S' : 'O';
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

    public static void main(String[] args) {
        launch(args);
    }
}