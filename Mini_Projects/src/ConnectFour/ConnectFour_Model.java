package ConnectFour;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ConnectFour_Model {
	protected static int COLUMNS = 7;
	protected static int ROWS = 6;
	
	public enum Moves{Red, Yellow};
	private Moves nextMove = Moves.Red;
	private Moves winner = null;
	
	protected Moves[][] discBoard = new Moves[COLUMNS][ROWS];
	protected int currentRow;
	protected int currentCol;

	String mode;

	public ConnectFour_Model() {
		discBoard = new Moves[COLUMNS][ROWS];
	}
	public String getMode(){
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}

	public Moves getWinner() {
		return winner;
	}

	// Needed to set the winner in gameOver() to null
	public void setWinner(Moves winner){
		this.winner = winner;
	}

	public void setBoardSize(String size){
		String[] values = size.split("x");
		int col = Integer.parseInt(values[0]);
		int row = Integer.parseInt(values[1]);
		COLUMNS = col;
		ROWS = row;
		if (mode.equals("ConnectFive")) {
			COLUMNS = 9;
			ROWS = 6;
		}
		discBoard = new Moves[COLUMNS][ROWS];
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
		mode = getMode();

		checkWinner(mode);
	}

	public Moves[][] resetDiscBoard(){
		discBoard = new Moves[COLUMNS][ROWS];
		winner = null;
		nextMove = Moves.Red;
		return discBoard;
	}


	public void checkWinner(String mode) {
		if (checkVertWinner(mode) != null) winner = checkVertWinner(mode);
		if (checkHorWinner(mode) != null) winner = checkHorWinner(mode);
		if (checkDiagWinnerDown(mode) != null) winner = checkDiagWinnerDown(mode);
		if (checkDiagWinnerUp(mode) != null) winner = checkDiagWinnerUp(mode);
	}
	

	// Check for horizontal winner - WORKS!!!!!
	private Moves checkHorWinner(String mode) {
		Moves hWinner = null;

		if (!mode.equals("ConnectFive")) {
			for (int x = 0; x < COLUMNS - 3; x++) {
				if (discBoard[x][currentRow] == discBoard[x + 1][currentRow] && discBoard[x][currentRow] == discBoard[x + 2][currentRow] &&
						discBoard[x][currentRow] == discBoard[x + 3][currentRow]) {
					hWinner = discBoard[x][currentRow];
				}
			}
		}

		if (mode.equals("ConnectFive")) {
			for (int x = 0; x < COLUMNS - 4; x++) {
				if (discBoard[x][currentRow] == discBoard[x + 1][currentRow] && discBoard[x][currentRow] == discBoard[x + 2][currentRow] &&
						discBoard[x][currentRow] == discBoard[x + 3][currentRow] && discBoard[x][currentRow] == discBoard[x + 4][currentRow]) {
					hWinner = discBoard[x][currentRow];
				}
			}
		}
		return hWinner;
	}

	// Check for vertical Winner - WORKS!!
	private Moves checkVertWinner(String mode) {
		Moves vertWinner = null;

		if (!mode.equals("ConnectFive")) {
			for (int y = 0; y < ROWS - 3; y++) {
				if (discBoard[currentCol][y] == discBoard[currentCol][y + 1] && discBoard[currentCol][y] == discBoard[currentCol][y + 2] &&
						discBoard[currentCol][y] == discBoard[currentCol][y + 3]) {
					vertWinner = discBoard[currentCol][y];
				}
			}
		}

		if (mode.equals("ConnectFive")) {
			for (int y = 0; y < ROWS - 4; y++) {
				if (discBoard[currentCol][y] == discBoard[currentCol][y + 1] && discBoard[currentCol][y] == discBoard[currentCol][y + 2] &&
						discBoard[currentCol][y] == discBoard[currentCol][y + 3] && discBoard[currentCol][y] == discBoard[currentCol][y + 4]) {
					vertWinner = discBoard[currentCol][y];
				}
			}
		}
		return vertWinner;
	}


	// Check for vertical winner from Top to Bottom
	//Exception because of Array IndexOutOfBound
	private Moves checkDiagWinnerDown(String mode) {
		Moves diagWinnerDown = null;

		if (!mode.equals("ConnectFive")) {
			try {
				if (discBoard[currentCol][currentRow] == discBoard[currentCol + 1][currentRow + 1] &&
						discBoard[currentCol][currentRow] == discBoard[currentCol + 2][currentRow + 2] &&
						discBoard[currentCol][currentRow] == discBoard[currentCol + 3][currentRow + 3])
					diagWinnerDown = discBoard[currentCol][currentRow];
			} catch (Exception e) {
				// do nothing when exception
			}
		}

		if (mode.equals("ConnectFive")) {
			try {
				if (discBoard[currentCol][currentRow] == discBoard[currentCol + 1][currentRow + 1] &&
						discBoard[currentCol][currentRow] == discBoard[currentCol + 2][currentRow + 2] &&
						discBoard[currentCol][currentRow] == discBoard[currentCol + 3][currentRow + 3] &&
						discBoard[currentCol][currentRow] == discBoard[currentCol + 4][currentRow + 4])
					diagWinnerDown = discBoard[currentCol][currentRow];
			} catch (Exception e) {
				// do nothing when exception
			}
		}
		return diagWinnerDown;
	}

	// Check for vertical winner from Bottom to top
	private Moves checkDiagWinnerUp(String mode) {
		Moves diagWinnerUp = null;

		if (!mode.equals("ConnectFive")) {
			try {
				if (discBoard[currentCol][currentRow] == discBoard[currentCol - 1][currentRow + 1] &&
						discBoard[currentCol][currentRow] == discBoard[currentCol - 2][currentRow + 2] &&
						discBoard[currentCol][currentRow] == discBoard[currentCol - 3][currentRow + 3])
					diagWinnerUp = discBoard[currentCol][currentRow];
			} catch (Exception e) {
				// do nothing when exception
			}
		}

		if (mode.equals("ConnectFive")) {
			try {
				if (discBoard[currentCol][currentRow] == discBoard[currentCol - 1][currentRow + 1] &&
						discBoard[currentCol][currentRow] == discBoard[currentCol - 2][currentRow + 2] &&
						discBoard[currentCol][currentRow] == discBoard[currentCol - 3][currentRow + 3] &&
						discBoard[currentCol][currentRow] == discBoard[currentCol - 4][currentRow + 4])
					diagWinnerUp = discBoard[currentCol][currentRow];
			} catch (Exception e) {
				// do nothing when exception
			}
		}
		return diagWinnerUp;
	}

	// method for showing rules for Connect4 in English
	public void showRule() {

		Alert rules = new Alert(Alert.AlertType.NONE, "To win Connect Four you must be the first player"
				+"\n to get four of your colored checkers in a row either horizontally, vertically"
				+"\n or diagonally. Contents: Connect Four will come with a grid, 2 end supports,"
				+"\n 21 red checkers and 21 black checkers, and the official Connect Four game rules"
				+"\n and instructions.");

		rules.setTitle("connect4-Rules");
		rules.getDialogPane().getButtonTypes().add(ButtonType.OK);
		rules.showAndWait();

	}
	// method for showing rules for Connect4 in German
	public void showRegeln() {

		Alert rules = new Alert(Alert.AlertType.NONE, "Um Connect Four zu gewinnen, müssen Sie der erste"
				+"\n Spieler sein, der vier Ihrer farbigen Steine in einer Reihe entweder horizontal,"
				+"\n vertikal oder diagonal erhält. Der Inhalt: Connect Four wird mit einem Gitter,"
				+"\n 2 Endstützen, 21 roten und 21 schwarzen Steinen und den offiziellen Spielregeln"
				+"\n und Anweisungen für Connect Four geliefert.\r\n" +
				"");

		rules.setTitle("connect4-Regeln");
		rules.getDialogPane().getButtonTypes().add(ButtonType.OK);
		rules.showAndWait();

	}

	// method for showing help for Connect4 in German
	public void showHilfe() {

		Alert rules = new Alert(Alert.AlertType.NONE, "Klicke auf die gewünschten Kolonne für den Spielzug!");

		rules.setTitle("connect4-Regeln");
		rules.getDialogPane().getButtonTypes().add(ButtonType.OK);
		rules.showAndWait();

	}

	// method for showing help for Connect4 in English
	public void showHelp() {

		Alert rules = new Alert(Alert.AlertType.NONE, "Click on the desired column for the play!");

		rules.setTitle("connect4-Rules");
		rules.getDialogPane().getButtonTypes().add(ButtonType.OK);
		rules.showAndWait();

	}
	

}
