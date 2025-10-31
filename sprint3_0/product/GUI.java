package sprint3_0.product;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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
    private Pane lineLayer = new Pane();
    private GridPane boardPane = new GridPane();
    private CheckBox checkBox1 = new CheckBox("Enable AI");

    @Override
    public void start(Stage primaryStage) {
        game = new SimpleGame(gridSize);
        console = new Console(game.getBoard());
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
            console = new Console(game.getBoard());
            drawNewBoard();
        });

        radioButton2.setOnAction(e -> {
            game = new GeneralGame(gridSize);
            console = new Console(game.getBoard());
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
            console = new Console(game.getBoard());
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
        HBox.setHgrow(boardPane, Priority.ALWAYS);

        lineLayer.setPickOnBounds(false); // allow clicks to pass through
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

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(controlBox);
        borderPane.setCenter(boardRow);
        borderPane.setBottom(statusBox);

        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setTitle("SOS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void drawBoard() {
    	boardPane.getChildren().clear();
    	lineLayer.getChildren().clear();
        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                squares[row][column] = new Square(row, column);
                boardPane.add(squares[row][column], column, row);
                char cell = game.getBoard().getCell(row, column);
                if (cell == 'S') {
                    squares[row][column].drawCross(); // or drawEss() if renamed later
                } else if (cell == 'O') {
                    squares[row][column].drawNought();
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

    public void drawNewBoard() {
        squares = new Square[gridSize][gridSize];
        drawBoard();
    }

    private char getSelectedLetterForCurrentPlayer() {
        return game.getCurrentPlayer() == 'X' ? (blueS.isSelected() ? 'S' : 'O') : (redS.isSelected() ? 'S' : 'O');
    }

    private void drawLineBetweenSquares(int r1, int c1, int r2, int c2, char player) {
    	
        Square start = squares[r1][c1];
        Square end = squares[r2][c2];

        Bounds startBounds = start.localToParent(start.getBoundsInLocal());
        Bounds endBounds = end.localToParent(end.getBoundsInLocal());

        double x1 = startBounds.getMinX() + startBounds.getWidth() / 2;
        double y1 = startBounds.getMinY() + startBounds.getHeight() / 2;
        double x2 = endBounds.getMinX() + endBounds.getWidth() / 2;
        double y2 = endBounds.getMinY() + endBounds.getHeight() / 2;

        Line line = new Line(x1, y1, x2, y2);
        line.setStroke(player == 'X' ? Color.BLUE : Color.RED);
        line.setStrokeWidth(4);
        lineLayer.getChildren().add(line);
        System.out.printf("Line from (%d,%d) to (%d,%d) → (%.1f, %.1f) to (%.1f, %.1f)%n",
        	    r1, c1, r2, c2, x1, y1, x2, y2);
    }

    public class Square extends Pane {
        private int row, column;

        public Square(int row, int column) {
            this.row = row;
            this.column = column;
            setStyle("-fx-border-color: white");
            this.setPrefSize(2000, 2000);
            this.setOnMouseClicked(e -> handleMouseClick());
        }

        private void handleMouseClick() {
            if (game.isGameOver()) return;

            char letter = getSelectedLetterForCurrentPlayer();
            if (game.makeMove(row, column, letter)) {
                drawBoard();
                console.displayMove(row, column);
                if (game.isGameOver()) {
                    gameStatus.setText("Game Over! Winner: " + game.getWinner());
                } else {
                    displayGameStatus();
                }
            }
        }

        public void drawCross() {
            Label sLabel = new Label("S");
            sLabel.setStyle("-fx-text-fill: black; -fx-font-size: 36px; -fx-font-weight: bold;");
            StackPane stack = new StackPane(sLabel);
            stack.prefWidthProperty().bind(this.widthProperty());
            stack.prefHeightProperty().bind(this.heightProperty());
            this.getChildren().add(stack);
        }

        public void drawNought() {
            Label oLabel = new Label("O");
            oLabel.setStyle("-fx-text-fill: black; -fx-font-size: 36px; -fx-font-weight: bold;");
            StackPane stack = new StackPane(oLabel);
            stack.prefWidthProperty().bind(this.widthProperty());
            stack.prefHeightProperty().bind(this.heightProperty());
            this.getChildren().add(stack);
        }

        private void displayGameStatus() {
            if (game.getCurrentPlayer() == 'X') {
                gameStatus.setText("Blue's Turn");
            } else {
                gameStatus.setText("Red's Turn");
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}