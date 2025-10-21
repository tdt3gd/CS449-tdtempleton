package sprint2_0.product;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GUI extends Application {

    private GameController controller;
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
    private CheckBox checkBox1 = new CheckBox("Enable AI");

    @Override
    public void start(Stage primaryStage) {
        controller = new GameController(gridSize);
        console = new Console(controller.getBoard());
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
        radioButton1.setOnAction(e -> controller.setGameMode(GameController.GameMode.SIMPLE));
        radioButton2.setOnAction(e -> controller.setGameMode(GameController.GameMode.GENERAL));

        boardSizeSelector.getItems().addAll(3, 4, 5, 6, 7, 8);
        boardSizeSelector.setValue(gridSize);
        boardSizeSelector.setPrefWidth(60);
        boardSizeSelector.setOnAction(e -> {
            gridSize = boardSizeSelector.getValue();
            controller = new GameController(gridSize);
            console = new Console(controller.getBoard());
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

        HBox boardRow = new HBox(20);
        boardRow.setAlignment(Pos.CENTER);
        boardRow.setPadding(new Insets(10));
        boardRow.getChildren().addAll(bluePlayerBox, boardPane, redPlayerBox);

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
        for (int row = 0; row < gridSize; row++) {
            for (int column = 0; column < gridSize; column++) {
                squares[row][column].getChildren().clear();
                char cell = controller.getBoard().getCell(row, column);
                if (cell == 'S') {
                    squares[row][column].drawCross();
                } else if (cell == 'O') {
                    squares[row][column].drawNought();
                }
            }
        }
    }

    public void drawNewBoard() {
        boardPane.getChildren().clear();
        squares = new Square[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                squares[i][j] = new Square(i, j);
                boardPane.add(squares[i][j], j, i);
            }
        }
        drawBoard();
    }

    private char getSelectedLetterForCurrentPlayer() {
        return controller.getCurrentTurn() == 'X' ? (blueS.isSelected() ? 'S' : 'O') : (redS.isSelected() ? 'S' : 'O');
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
            char letter = getSelectedLetterForCurrentPlayer();
            if (controller.makeMove(row, column, letter)) {
                drawBoard();
                console.displayMove(row, column);
                displayGameStatus();
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
            if (controller.getCurrentTurn() == 'X') {
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