package ConnectFour;

import java.util.ArrayList;
import java.util.List;

import ConnectFour.ConnectFour_Model.Moves;

import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class ConnectFour_View {
	private final ConnectFour_Model model;
	private final Stage stage;
	
	private static final int TILE_SIZE = 80;
	Shape shape;
	private List<Rectangle> overlay;
	
	private Pane discs;
	
	
	public ConnectFour_View (Stage stage, ConnectFour_Model model) {
		this.stage = stage;
		this.model = model;
		
		Pane root = new Pane();
		
		discs = new Pane();
		root.getChildren().addAll(discs);
		shape = makeGrid();
		root.getChildren().add(shape);
		root.getChildren().addAll(makeOverlay());
		
		Scene inGameScene = new Scene(root);
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
		Disc disc;
		int row = model.currentRow;
		int column = model.currentCol;
		
			if (model.discBoard[model.currentCol][model.currentRow] == Moves.R) {
				disc = new Disc(true);
			} else {
				disc = new Disc(false);
			}
			discs.getChildren().add(disc);
			disc.setTranslateX(model.currentCol * (TILE_SIZE + 5) + TILE_SIZE / 4);
			disc.setTranslateY(model.currentRow * (TILE_SIZE + 5) + TILE_SIZE / 4);
			
			
			
			// TODO Create Animation => .setToY for setTranslateY
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
	
	// TODO Create a method to place disc in playing board visually with animation
	
}
