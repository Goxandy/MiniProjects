package ConnectFour;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

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
	private final ConnectFour_Model model;
	private final Stage stage;
	private Pane root;

	Scene inGameScene;
	private static final int TILE_SIZE = 80;
	Shape shape;
	private List<Rectangle> overlay;

	protected Disc disc;
	private Pane discPane;
	private ArrayList<Disc> discsToRemove = new ArrayList<>();

	Scene sceneGO;
	protected Button exit;
	
	
	public ConnectFour_View (Stage stage, ConnectFour_Model model) {
		this.stage = stage;
		this.model = model;
		
		root = new Pane();
		
		discPane = new Pane();
		root.getChildren().addAll(discPane);
		shape = makeGrid();
		root.getChildren().add(shape);
		root.getChildren().addAll(makeOverlay());
		
		inGameScene= new Scene(root);
		inGameScene.getStylesheets().add(getClass().getResource("ConnectFour.css").toExternalForm());

		stage.setScene(inGameScene);
		stage.setTitle("Connect Four");
	}
	
	public void start() {
		stage.show();
	}
	
	public void stop() {
		stage.hide();
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
	
	public List<Rectangle> getOverlay(){
		return overlay;
	}
	
	public void placeDisc() {
		boolean color;

		int row = model.currentRow;
		int column = model.currentCol;
		
			if (model.discBoard[model.currentCol][model.currentRow] == Moves.Red) {
				this.disc = new Disc(true);
			} else {
				disc = new Disc(false);
			}
		discPane.getChildren().add(disc);
		disc.setTranslateX(model.currentCol * (TILE_SIZE + 5) + TILE_SIZE / 4);
		discsToRemove.add(disc);


		TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5), disc);
		animation.setToY(model.currentRow * (TILE_SIZE + 5) + TILE_SIZE / 4);
		animation.play();

		final Object lock = new Object();

		animation.setOnFinished( e -> {
			if (model.getWinner() != null){
				try {
					// make the Game Over scene wait to show up to avoid rushing to the endScene
					synchronized (lock) {
						lock.wait(1500);
					}
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				gameOver();
			}
		});
	}

	// create what happens when game is over
	public void gameOver(){

		Pane rootGameOver = new Pane();
		sceneGO = new Scene(rootGameOver, 800, 650);
		sceneGO.getStylesheets().add(getClass().getResource("ConnectFour.css").toExternalForm());


		Background background = new Background(new BackgroundFill(Color.rgb(13, 22, 200), null, null));
		// rootGameOver.setBackground(background);

		Label gameOverText = new Label("What a Game!! Player " + model.getWinner() + " is the winner");
		gameOverText.setTranslateX(150);
		gameOverText.setTranslateY(80);

		Button playAgain = new Button("Play Again");
		playAgain.setTranslateX(150);
		playAgain.setTranslateY(500);
		
		
		// Does not work - changes to scene and resets the view but not the logic
		playAgain.setOnAction( e -> {
			discPane.getChildren().removeAll(discsToRemove);
			model.resetDiscBoard();
			stage.setScene(inGameScene);
		});

		exit = new Button("Exit");
		exit.setTranslateX(470);
		exit.setTranslateY(500);
		exit.setMinWidth(175);

		// Works! TODO Place setOnAction in Controller
		exit.setOnAction( e -> {
			stop();
		});


		rootGameOver.getChildren().add(exit);
		rootGameOver.getChildren().add(gameOverText);
		rootGameOver.getChildren().add(playAgain);

		try {
			Image gameOver = new Image("file:gameOver.png");
			ImageView iv = new ImageView();
			iv.setImage(gameOver);
			iv.setPreserveRatio(true);
			iv.setFitWidth(400);
			iv.setTranslateX(200);
			iv.setTranslateY(210);

			rootGameOver.getChildren().add(iv);
			stage.setScene(sceneGO);


		} catch (Exception e) {
			e.printStackTrace();
		}

		stage.setTitle("Game Over");

	}

	public Disc getDisc() {
		return disc;
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
