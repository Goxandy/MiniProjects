package ConnectFour;


public class ConnectFour_Model {
	protected static final int COLUMNS = 7;
	protected static final int ROWS = 6;
	
	private final int[][] board; // An array to place the discs in the right position
	private boolean redMove = true;
	
	public ConnectFour_Model() {
		board = new int[COLUMNS][ROWS];
	}
	
	
	
	public boolean isRedMove() {
		return redMove;
	}
	
	// probably not needed
	public void setRedMove(boolean redMove) {
		this.redMove = redMove;
	}

}
