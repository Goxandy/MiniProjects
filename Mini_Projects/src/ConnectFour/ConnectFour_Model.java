package ConnectFour;

import ConnectFour.ConnectFour_Model.Moves;

public class ConnectFour_Model {
	protected static final int COLUMNS = 7;
	protected static final int ROWS = 6;
	
	public enum Moves{R, Y};
	private Moves nextMove = Moves.R;
	private Moves winner = null;
	
	protected Moves[][] discBoard = new Moves[COLUMNS][ROWS];
	protected int currentRow;
	protected int currentCol;

	public ConnectFour_Model() {
		
	}
	
	public void makeMove(int col) {
		int row = ROWS -1;
		do {
			if (discBoard[col][row] == null) break;
				row--;	
		} while (row >= 0);
		
		if (row < 0) return;
		
		currentCol = col;
		currentRow = row;
		
		discBoard[currentCol][currentRow] = nextMove;
		nextMove = (nextMove == Moves.R  ? Moves.Y : Moves.R);	
		
		if (checkVertWinner() != null) winner = checkVertWinner();
		if (checkHorWinner() != null) winner = checkHorWinner();
		
	}
	
	// Check for horizontal winner - WORKS!!!!!
	public Moves checkHorWinner() {
		Moves hWinner = null;
				
			for (int y = 0; y < ROWS; y++) {
				for (int x = 0; x < COLUMNS-3; x++) {
					if (discBoard[x][y] == discBoard[x+1][y] && discBoard[x][y] == discBoard[x+2][y] &&discBoard[x][y] == discBoard[x+3][y]) {
						hWinner = discBoard[x][y];
					}
				}
			}
		return hWinner;
	}
	
	// Check for vertical Winner - not working :(
	public Moves checkVertWinner() {
		Moves vertWinner = null;
		
			for (int x = 0; x < COLUMNS; x++) {
				for (int y = 0; y < ROWS - 3; y++) {
				if (discBoard[x][y] == discBoard[x][y+1] && discBoard[x][y] == discBoard[x][y+2] && discBoard[x][y] == discBoard[x][y+3]) {
					vertWinner = discBoard[x][y];
				}
			}	
		}
		return vertWinner;
	}
	
	
	public Moves getWinner() {
		return winner;
	}
}
