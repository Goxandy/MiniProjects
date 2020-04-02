package ConnectFour;

public class ConnectFour_Model {
	protected static int COLUMNS = 7;
	protected static int ROWS = 6;
	
	public enum Moves{Red, Yellow};
	private Moves nextMove = Moves.Red;
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
		nextMove = (nextMove == Moves.Red  ? Moves.Yellow : Moves.Red);
		
		checkWinner();
	}

	public Moves[][] resetDiscBoard(){
		discBoard = new Moves[COLUMNS][ROWS];
		winner = null;
		nextMove = Moves.Red;
		return discBoard;
	}

	public void setBoardSize(String size){
		String[] values = size.split("x");
		int col = Integer.parseInt(values[0]);
		int row = Integer.parseInt(values[1]);
		COLUMNS = col;
		ROWS = row;
		discBoard = new Moves[COLUMNS][ROWS];
	}

	public void checkWinner() {
		if (checkVertWinner() != null) winner = checkVertWinner();
		if (checkHorWinner() != null) winner = checkHorWinner();
		if (checkDiagWinnerDown() != null) winner = checkDiagWinnerDown();
		if (checkDiagWinnerUp() != null) winner = checkDiagWinnerUp();
	}
	
	// Check for horizontal winner - WORKS!!!!!
	private Moves checkHorWinner() {
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
	private Moves checkVertWinner() {
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
	private Moves checkDiagWinnerDown() {
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
	private Moves checkDiagWinnerUp() {
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

	// Needed to set the winner in gameOver() to null
	public void setWinner(Moves winner){
		this.winner = winner;
	}
}
