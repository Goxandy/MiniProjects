package ConnectFour;

import java.util.ArrayList;
import java.util.List;


import ConnectFour.ConnectFour_Model.Moves;

import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Label;
/**
 * 
 * @author ConnectFour:


TODO:

PreFame-Fenster(Andy):
Spielername eingeben; Grösse vom Spielboard auswählen; Zusatzmodus (5er System);
Tetris-Version




InGame-fenster(Levin):

-MenuBar (Sprache wechseln; Hintergrundfarbe wechseln; Spielregeln anzeigen)


EndGame-Fenster(Levin):

-Zeitaufwand anzeigen (Score)

 *
 */

public class ConnectFour_View {
	// General objects needed by all scenes
	private final ConnectFour_Model model;
	protected final Stage stage;

	// Elements to display the playing board (inGameScene)
	Scene inGameScene;
	private static final int TILE_SIZE = 80;
	Shape shape;
	private List<Rectangle> overlay;

	// Elements to display the game flow (inGameScene)
	protected Disc disc;
	protected Pane discPane;
	protected ArrayList<Disc> discsToRemove = new ArrayList<>();
	TranslateTransition animation = new TranslateTransition();

	// Elements to display the GameOver scene
	protected Scene gameOverScene;
	protected Button exit = new Button("Exit");
	protected Button playAgain = new Button("Play Again");

	// Elements to control start scene
	protected Button startBtn = new Button("Start Game");
	protected Scene startScene;
	protected Label lblSize = new Label("Choose the size of the playing board");
	protected ChoiceBox size = new ChoiceBox();
	
	public ConnectFour_View (Stage stage, ConnectFour_Model model) {
		this.stage = stage;
		this.model = model;

		inGameScene = createGameScene();
		startScene = createStartScene();
		stage.setScene(startScene);
		stage.setTitle("Connect Four");
	}
	
	public void start() {
		stage.show();
	}
	
	public void stop() {
		stage.hide();
	}

	public void changeScene(Scene sceneToGo) {
		stage.setScene(sceneToGo);
	}

	public void updateScene() {
		startScene = createStartScene();
		inGameScene = createGameScene();
		gameOverScene = createGameOverScene();
	}

	public List<Rectangle> getOverlay(){
		return overlay;
	}

	public Disc getDisc() {
		return disc;
	}

	private Scene createStartScene() {
		Pane root = new Pane();
		startBtn.setTranslateX(470);
		startBtn.setTranslateY(500);

		lblSize.setTranslateX(50);
		lblSize.setTranslateY(200);

		size.setTranslateX(500);
		size.setTranslateY(200);
		size.getItems().add("7x6");
		size.getItems().add("5x4");
		size.getItems().add("6x5");
		size.getItems().add("8x7");
		size.getItems().add("9x7");
		size.getItems().add("10x7");
		size.getItems().add("8x8");
		size.setTooltip(new Tooltip("Standard Mode is 7x6 (column x row)"));

		root.getChildren().addAll(startBtn, lblSize, size);

		Scene scene = new Scene(root, 800, 650);
		scene.getStylesheets().add(getClass().getResource("ConnectFour.css").toExternalForm());
		return scene;
	}

	private Scene createGameScene(){
		Pane root = new Pane();
		discPane = new Pane();
		root.getChildren().addAll(discPane);
		shape = makeGrid();
		root.getChildren().add(shape);
		root.getChildren().addAll(makeOverlay());
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("ConnectFour.css").toExternalForm());
		return scene;
	}
	
	// Create the playing board - the circles are not objects so we need an overlay
	private Shape makeGrid() {
		Shape shape = new Rectangle((model.COLUMNS + 1) * TILE_SIZE, (model.ROWS + 1) * TILE_SIZE);
		
		for (int y = 0; y < model.ROWS; y++) {
			for (int x = 0; x < model.COLUMNS; x++) {
				Circle circle = new Circle(TILE_SIZE / 2);
				circle.setCenterX(TILE_SIZE / 2);
				circle.setCenterY(TILE_SIZE / 2);
				circle.setTranslateX(x * (TILE_SIZE + 5) + TILE_SIZE / 4);
				circle.setTranslateY(y * (TILE_SIZE + 5) + TILE_SIZE / 4);
				
				shape = Shape.subtract(shape, circle);
			}
		}
		
		Light.Distant light = new Light.Distant();
		light.setAzimuth(45.0);
		light.setElevation(30.0);

	    Lighting lighting = new Lighting();
		lighting.setLight(light);
		lighting.setSurfaceScale(5.0);
		
		shape.setEffect(lighting);
		 
		shape.setFill(Color.BLUE);
		return shape;
	}
	
	// create an overlay to highlight the desired column
	private List<Rectangle> makeOverlay() {
		overlay = new ArrayList();

			for (int x = 0; x < model.COLUMNS; x++) {
				Rectangle rect = new Rectangle(TILE_SIZE, (model.ROWS + 1) * TILE_SIZE);
				rect.setTranslateX(x * (TILE_SIZE + 5) + TILE_SIZE / 4);
				rect.setFill(Color.TRANSPARENT);
				
				overlay.add(rect);
			}
		return overlay;
	}
	

	
	public void placeDisc() {
		int row = model.currentRow;
		int column = model.currentCol;
		
			if (model.discBoard[column][row] == Moves.Red) {
				this.disc = new Disc(true);
			} else {
				disc = new Disc(false);
			}
		discPane.getChildren().add(disc);
		disc.setTranslateX(column * (TILE_SIZE + 5) + TILE_SIZE / 4);
		discsToRemove.add(disc);


		animation.setDuration(Duration.seconds(0.3));
		animation.setNode(disc);
		animation.setToY(row * (TILE_SIZE + 5) + TILE_SIZE / 4);
		animation.play();
	}

	// create what happens when game is over
	public Scene createGameOverScene(){

		Pane rootGameOver = new Pane();
		Scene scene  = new Scene(rootGameOver, 800, 650);


		Label gameOverText = new Label("What a Game!! Player " + model.getWinner() + " is the winner");
		gameOverText.setTranslateX(150);
		gameOverText.setTranslateY(80);

		playAgain.setTranslateX(150);
		playAgain.setTranslateY(500);

		exit.setTranslateX(470);
		exit.setTranslateY(500);
		exit.setMinWidth(175);

		ImageView iv = new ImageView();
		iv.setPreserveRatio(true);
		iv.setFitWidth(400);
		iv.setTranslateX(200);
		iv.setTranslateY(210);

		rootGameOver.getChildren().add(exit);
		rootGameOver.getChildren().add(gameOverText);
		rootGameOver.getChildren().add(playAgain);
		rootGameOver.getChildren().add(iv);

		try {
			Image gameOver = new Image("file:gameOver.png");
			iv.setImage(gameOver);
		} catch (Exception e) {
			e.printStackTrace();
		}
		scene.getStylesheets().add(getClass().getResource("ConnectFour.css").toExternalForm());
		return scene;
	}

	private class Disc extends Circle {
		private boolean color;
		private static final int TILE_SIZE = 80;
		
		public Disc(boolean red) {
			super(TILE_SIZE / 2, (red ? Color.RED : Color.YELLOW));
			this.color = red;	
			
			setCenterX(TILE_SIZE / 2);
			setCenterY(TILE_SIZE / 2);
		}
	}
}
