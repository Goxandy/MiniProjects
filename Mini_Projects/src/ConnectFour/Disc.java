package ConnectFour;



import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Disc extends Circle {
	private final boolean red;
	private static final int TILE_SIZE = 80;
	
	public Disc(boolean red) {
		super(TILE_SIZE / 2, (red ? Color.RED : Color.YELLOW));
		this.red = red;	
		
		setCenterX(TILE_SIZE / 2);
		setCenterY(TILE_SIZE / 2);
	}
}
