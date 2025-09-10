package sprint0_0.product;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class GUI extends Application {

	private Square[][] squares;

	private Label gameStatus = new Label("X's Turn");
	
	private Label radioButtonLabel = new Label("Sample radio buttons. Select 1, 2 or 3");
	
	private Label horizLineLabel = new Label("Sample horizontal line");
	
	private Line horizLine = new Line();
	
	private RadioButton radioButton1 = new RadioButton("1");
	private RadioButton radioButton2 = new RadioButton("2");
	private RadioButton radioButton3 = new RadioButton("3");
	private ToggleGroup radioBtnGroup;
	
	private CheckBox checkBox1 = new CheckBox("Check this box. It doesn't do anything (yet).");
	
	static private Board board;

	@Override
	public void start(Stage primaryStage) {
		if (board == null) {
			board = new Board();
		}
		GridPane pane = new GridPane();
		squares = new Square[8][8];
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				pane.add(squares[i][j] = new Square(i, j), j, i);
		drawBoard();

		BorderPane borderPane = new BorderPane();
		horizLine.setStartX(50);
		horizLine.setEndX(400);
		VBox vertBox = new VBox(10);
		radioBtnGroup = new ToggleGroup();
		radioButton1.setToggleGroup(radioBtnGroup);
		radioButton2.setToggleGroup(radioBtnGroup);
		radioButton3.setToggleGroup(radioBtnGroup);
		vertBox.getChildren().addAll(horizLineLabel, horizLine, radioButtonLabel, radioButton1,radioButton2,radioButton3,checkBox1);

		borderPane.setTop(vertBox);
		borderPane.setCenter(pane);
		borderPane.setBottom(gameStatus);
		

		Scene scene = new Scene(borderPane, 450, 550);
		primaryStage.setTitle("Sprint0 sample GUI");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void drawBoard() {
		for (int row = 0; row < 8; row++)
			for (int column = 0; column < 8; column++) {
				squares[row][column].getChildren().clear();
				if (board.getCell(row, column) == 1)
					squares[row][column].drawCross();
				else if (board.getCell(row, column) == 2)
					squares[row][column].drawNought();
			}

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
			board.makeMove(row, column);
			drawBoard();
			displayGameStatus();
		}

		public void drawCross() {
			Line line1 = new Line(10, 10, this.getWidth() - 10, this.getHeight() - 10);
			line1.endXProperty().bind(this.widthProperty().subtract(10));
			line1.endYProperty().bind(this.heightProperty().subtract(10));
			line1.setStyle("-fx-stroke: red;");
			line1.setStrokeWidth(5.0);
			Line line2 = new Line(10, this.getHeight() - 10, this.getWidth() - 10, 10);
			line2.startYProperty().bind(this.heightProperty().subtract(10));
			line2.endXProperty().bind(this.widthProperty().subtract(10));
			line2.setStyle("-fx-stroke: red;");
			line2.setStrokeWidth(5.0);
			this.getChildren().addAll(line1, line2);
		}

		public void drawNought() {
			Ellipse ellipse = new Ellipse(this.getWidth() / 2, this.getHeight() / 2, this.getWidth() / 2 - 10,
					this.getHeight() / 2 - 10);
			ellipse.centerXProperty().bind(this.widthProperty().divide(2));
			ellipse.centerYProperty().bind(this.heightProperty().divide(2));
			ellipse.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));
			ellipse.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));
			ellipse.setStroke(Color.RED);
			ellipse.setStrokeWidth(5.0);
			ellipse.setFill(Color.TRANSPARENT);

			getChildren().add(ellipse);
		}

		private void displayGameStatus() {
			if (board.getTurn() == 'X') {
				gameStatus.setText("X's Turn");
			} else {
				gameStatus.setText("O's Turn");
			}
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
