package ConnectFour;

import java.util.ArrayList;
import java.util.List;
import ConnectFour.ConnectFour_Model.Moves;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

/**
 * 
 * @author ConnectFour

TODO:

-----------------------------------------------------------------------------------------------------------------------
PreFame-Fenster(Andy):
Spielername eingeben; Grösse vom Spielboard auswählen; Zusatzmodus (5er System);
-----------------------------------------------------------------------------------------------------------------------

InGame-Fenster(Levin):
-MenuBar (Sprache wechseln; Hintergrundfarbe wechseln; Spielregeln anzeigen)
EndGame-Fenster(Levin):
-Zeitaufwand anzeigen (Time)
-----------------------------------------------------------------------------------------------------------------------
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
	protected VBox root;

	// Elements to display the game flow (inGameScene)
	protected Disc disc;
	protected Pane discPane;
	protected ArrayList<Disc> discsToRemove = new ArrayList<>();
	TranslateTransition animation = new TranslateTransition();


	// Elements to control the GameOver scene
	protected Scene gameOverScene;
	protected Button exit = new Button("Exit");
	protected Button playAgain = new Button("Play Again");
	protected Label gameOverText = new Label();
	protected Button btnResult = new Button("See result");
	protected Label lblGameTime = new Label();

	// Elements to control start scene
	protected Button startBtn = new Button("Start Game");
	protected Scene startScene;
	protected Label lblBoardSize = new Label("Choose the size of the playing board");
	protected ChoiceBox boardSize = new ChoiceBox();
	protected Label lblMode = new Label("Choose the mode:");
	protected ChoiceBox mode = new ChoiceBox();

	// Elements to display for the MenuBar, Menu and MenuItems
	protected MenuBar menuBar;

	protected Menu gameMenu;
	protected Menu gameLanguage;
	protected Menu gameRules;
	protected Menu gameBackgrounds;

	protected MenuItem newGame;
	protected MenuItem resetGame;
	protected MenuItem exitGame;

	protected MenuItem germanLanguage;
	protected MenuItem englishLanguage;

	protected MenuItem connect4Rules;
	protected MenuItem connect4Help;

	protected MenuItem background1;
	protected MenuItem background2;
	protected MenuItem background3;
	



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
		GridPane root = new GridPane();
		root.setPadding(new Insets(60));
		root.setVgap(50);
		root.setAlignment(Pos.CENTER);
		Label lblTitle = new Label("Connect Four by Levin & Andreas");
		lblTitle.getStyleClass().add("title");
		lblTitle.setMinWidth(700);
		

		boardSize.getItems().removeAll(boardSize.getItems());
		boardSize.getItems().add("7x6");
		boardSize.getItems().add("5x4");
		boardSize.getItems().add("6x5");
		boardSize.getItems().add("8x7");
		boardSize.getItems().add("9x7");
		boardSize.getItems().add("10x7");
		boardSize.getItems().add("8x8");
		boardSize.setTooltip(new Tooltip("Default Mode is 7x6 (column x row)"));
		

			
		lblMode.setMinWidth(461);
		
		mode.getItems().removeAll(boardSize.getItems());
		mode.getItems().add("ConnectFour");
		mode.getItems().add("ConnectFive");
		// mode.getItems().add("Linetris");
		mode.setTooltip(new Tooltip("Default mode is ConnectFour"));
		

		root.add(lblTitle, 0, 0, 2, 1);
		root.add(lblBoardSize, 0, 1);
		root.add(lblMode, 0, 2);
		root.add(boardSize, 1, 1);
		root.add(mode, 1, 2);
		root.add(startBtn, 1, 3);


		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("settings.css").toExternalForm());
		return scene;
	}
	
	public void resetStartScene() {
		startScene = null;
	}

	private Scene createGameScene(){
		root = new VBox();
		Pane gameRoot = new Pane();
		discPane = new Pane();
		gameRoot.getChildren().add(discPane);
		shape = makeGrid();


		// creating MenuBar, Menu and MenuItems

		menuBar = new MenuBar();

		gameMenu = new Menu("Connect4");
		gameRules = new Menu("Rules");
		gameLanguage = new Menu("Language");
		gameBackgrounds = new Menu("Backgrounds");

		newGame = new MenuItem("New Game");
		resetGame = new MenuItem("Reset");
		exitGame = new MenuItem("Exit");

		germanLanguage = new MenuItem("German");
		englishLanguage = new MenuItem("English");

		connect4Rules = new MenuItem("Rules");
		connect4Help = new MenuItem("Help");

		background1 = new MenuItem("green");
		background2 = new MenuItem("light-blue");
		background3 = new MenuItem("white");

		menuBar.getMenus().addAll(gameMenu, gameRules, gameBackgrounds, gameLanguage);

		gameMenu.getItems().addAll(newGame,resetGame, exitGame);
		gameLanguage.getItems().addAll(germanLanguage, englishLanguage);
		gameRules.getItems().addAll(connect4Rules, connect4Help);
		gameBackgrounds.getItems().addAll(background1, background2, background3);



		gameRoot.getChildren().add(shape);
		gameRoot.getChildren().addAll(makeOverlay());
		root.getChildren().addAll(menuBar, gameRoot);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("settings.css").toExternalForm());
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

	// create what happens when game is over
	public Scene createGameOverScene(){

		Pane rootGameOver = new Pane();
		Scene scene  = new Scene(rootGameOver, 800, 700);


		gameOverText.setText("What a Game!! Player " + model.getWinner() + " is the winner");
		if (model.fullPlayingBoard() == true ) gameOverText.setText("What a Game! It's a draw");
		gameOverText.setMinWidth(500);
		gameOverText.setTranslateX(150);
		gameOverText.setTranslateY(80);

		playAgain.setTranslateX(150);
		playAgain.setTranslateY(500);
		
		btnResult.setTranslateX(150);
		btnResult.setTranslateY(600);
		btnResult.setMinWidth(playAgain.prefWidthProperty().getValue());

		exit.setTranslateX(470);
		exit.setTranslateY(500);
		exit.setMinWidth(175);


		lblGameTime.setTranslateX(470);
		lblGameTime.setTranslateY(600);

		ImageView iv = new ImageView();
		iv.setPreserveRatio(true);
		iv.setFitWidth(400);
		iv.setTranslateX(200);
		iv.setTranslateY(210);

		rootGameOver.getChildren().add(exit);
		rootGameOver.getChildren().add(gameOverText);
		rootGameOver.getChildren().add(playAgain);
		rootGameOver.getChildren().add(btnResult);
		rootGameOver.getChildren().add(lblGameTime);

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
	
	/*
	public void updateDiscBoard() {
		discPane.getChildren().removeAll(discsToRemove);
		for (int x = 0; x < model.COLUMNS; x++) {
			for (int y = 0; y < model.ROWS; y++) {
				if(model.discBoard[x][y] == Moves.Red) {
					disc = new Disc(true);
				} else {
					disc = new Disc(false);
				}
				discPane.getChildren().add(disc);
				disc.setTranslateX(x * (TILE_SIZE + 5) + TILE_SIZE / 4);
				disc.setTranslateY(y * (TILE_SIZE + 5) + TILE_SIZE / 4);
				discsToRemove.add(disc);
			}
		}
	}
*/


	public void prepareBoard(){
		model.makeMove(8);
		placeDiscNoAnimation();
		model.makeMove(7);
		placeDiscNoAnimation();
		model.makeMove(7);
		placeDiscNoAnimation();
		model.makeMove(8);
		placeDiscNoAnimation();
		model.makeMove(8);
		placeDiscNoAnimation();
		model.makeMove(7);
		placeDiscNoAnimation();
		model.makeMove(7);
		placeDiscNoAnimation();
		model.makeMove(8);
		placeDiscNoAnimation();
		model.makeMove(8);
		placeDiscNoAnimation();
		model.makeMove(7);
		placeDiscNoAnimation();
		model.makeMove(7);
		placeDiscNoAnimation();
		model.makeMove(8);
		placeDiscNoAnimation();
	}

	private void placeDiscNoAnimation() {
		int row = model.currentRow;
		int column = model.currentCol;

		if (model.discBoard[column][row] == Moves.Red) {
			this.disc = new Disc(true);
		} else {
			disc = new Disc(false);
		}
		discPane.getChildren().add(disc);
		disc.setTranslateX(column * (TILE_SIZE + 5) + TILE_SIZE / 4);
		disc.setTranslateY(row * (TILE_SIZE + 5) + TILE_SIZE / 4);
		discsToRemove.add(disc);
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
