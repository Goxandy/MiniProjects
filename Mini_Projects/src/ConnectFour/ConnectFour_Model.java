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
		
		try {
			if (checkDiagWinnerDown() != null) winner = checkDiagWinnerDown();
		} catch (Exception e) {
			// Do nothing when exception is catched
		}
		
		try {
			if (checkDiagWinnerUp() != null) winner = checkDiagWinnerUp();
		} catch (Exception e) {
			// Do nothing when exception is catched
		}
		
	}
	
	// Check for horizontal winner - WORKS!!!!!
	public Moves checkHorWinner() {
		Moves hWinner = null;
				
		for (int x = 0; x < COLUMNS-3; x++) {
			if (discBoard[x][currentRow] == discBoard[x+1][currentRow] && discBoard[x][currentRow] == discBoard[x+2][currentRow] && 
				discBoard[x][currentRow] == discBoard[x+3][currentRow]) {
					hWinner = discBoard[x][currentRow];
				}
			}	
		return hWinner;
	}
	
	// Check for vertical Winner - WORKS!!
	public Moves checkVertWinner() {
		Moves vertWinner = null;
		
		for (int y = 0; y < ROWS - 3; y++) {
			if (discBoard[currentCol][y] == discBoard[currentCol][y+1] && discBoard[currentCol][y] == discBoard[currentCol][y+2] && 
				discBoard[currentCol][y] == discBoard[currentCol][y+3]) {
					vertWinner = discBoard[currentCol][y];
			}
		}
	return vertWinner;
	}
	
	
	// Check for vertical winner from Top to Bottom
	//Exception because of Array IndexOutOfBound
	public Moves checkDiagWinnerDown() throws Exception {
		Moves diagWinnerDown = null;
		
		try {
			if (discBoard[currentCol][currentRow] == discBoard[currentCol+1][currentRow+1] && 
					discBoard[currentCol][currentRow] == discBoard[currentCol+2][currentRow+2] &&
					 discBoard[currentCol][currentRow] == discBoard[currentCol+3][currentRow+3]) 
				diagWinnerDown = discBoard[currentCol][currentRow];
		} catch (Exception e) {
			// do nothing when exception
		}
		
		return diagWinnerDown;
	}
	
	// Check for vertical winner from Bottom to top
	public Moves checkDiagWinnerUp() throws Exception {
		Moves diagWinnerUp = null;
		
		try {
			if (discBoard[currentCol][currentRow] == discBoard[currentCol-1][currentRow+1] && 
					discBoard[currentCol][currentRow] == discBoard[currentCol-2][currentRow+2] &&
					 discBoard[currentCol][currentRow] == discBoard[currentCol-3][currentRow+3]) 
				diagWinnerUp = discBoard[currentCol][currentRow];
		} catch (Exception e) {
			// do nothing when exception
		}
		
		return diagWinnerUp;
	}
	
	public Moves getWinner() {
		return winner;
	}
}
