package ConnectFour;

import java.util.ArrayList;
import java.util.List;


import ConnectFour.ConnectFour_Model.Moves;

import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	protected Button exit = new Button("Exit");
	protected Button playAgain = new Button("Play Again");
	
	
	public ConnectFour_View (Stage stage, ConnectFour_Model model) {
		this.stage = stage;
		this.model = model;

		inGameScene = createGameScene();
		stage.setScene(inGameScene);
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

	public List<Rectangle> getOverlay(){
		return overlay;
	}

	public Disc getDisc() {
		return disc;
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


		animation.setDuration(Duration.seconds(0.5));
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
