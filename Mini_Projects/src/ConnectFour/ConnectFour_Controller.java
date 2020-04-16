package ConnectFour;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ConnectFour_Controller {
	private final ConnectFour_Model model;
	private final ConnectFour_View view;

	public ConnectFour_Controller(ConnectFour_Model model, ConnectFour_View view) {
		this.model = model;
		this.view = view;

		view.startBtn.setOnAction(e -> {
			String mode = (String) view.mode.getValue();
			if (view.mode.getValue() == null) {
				model.setMode("ConnectFour");
				mode = "ConnectFour";
			}
			model.setMode(mode);
			if (view.boardSize.getValue() != null)
				model.setBoardSize((String) view.boardSize.getValue());
			if (mode.equals("ConnectFive")) model.setBoardSize("9x6");
			view.updateScene();
			view.changeScene(view.inGameScene);
			handleGameAction();
			if (mode.equals("ConnectFive")) {
				view.prepareBoard();
			}

		});

		view.playAgain.setOnAction(e -> {
			view.discPane.getChildren().removeAll(view.discsToRemove);
			model.resetDiscBoard();
			view.resetStartScene();
			view.updateScene();
			view.changeScene(view.startScene);
		});

		view.exit.setOnAction(e -> {
			view.stop();
		});
		
		view.btnResult.setOnAction( e -> {
			view.changeScene(view.inGameScene);
			disableDiscPlacement();
			
		});

	}

	private void handleGameAction() {
		// Display the overlay so the user know at which column he's aiming at
		for (Rectangle r : view.getOverlay()) {
			r.setOnMouseEntered(e -> r.setFill(Color.rgb(200, 200, 50, 0.3)));
			r.setOnMouseExited(e -> r.setFill(Color.TRANSPARENT));
		}

		// TODO Set the action to place a disc in the grid

		for (int i = 0; i < model.COLUMNS; i++) {
			int column = i;

			view.getOverlay().get(column).setOnMouseClicked(e -> {
				model.makeMove(column);
				view.placeDisc();
			});
		}

		view.animation.setOnFinished(e -> {
			/*if (model.mode.equals("Linetris") && model.checkLastRowFull() == true) {
				view.updateDiscBoard();
			}*/
			final Object lock = new Object();
			if (model.getWinner() != null || model.fullPlayingBoard() == true) {
				try {
					// make the Game Over scene wait to show up to avoid rushing to the endScene
					synchronized (lock) {
						lock.wait(1500);
					}
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				view.changeScene(view.createGameOverScene());
			}
		});

		// exit game when playing
		view.exitGame.setOnAction(e -> {
			view.stop();
		});

		// restart game when playing
		view.restartGame.setOnAction(e -> {
			view.discPane.getChildren().removeAll(view.discsToRemove);
			model.resetDiscBoard();
			view.changeScene(view.inGameScene);
			handleGameAction();
		});


		// TODO (not working efficient !!!!!!!)

		// show the "rules-window" given by the chosen language
		if (view.germanLanguage.getText() == "Deutsch") {
			view.connect4Rules.setOnAction(e -> model.showRegeln());
		}

		if (view.germanLanguage.getText() == "German") {
			view.connect4Rules.setOnAction(e -> model.showRule());
		}


		// show the "help-window" given by the chosen language
		if (view.germanLanguage.getText() == "Deutsch") {
			view.connect4Help.setOnAction(e -> model.showHilfe());
		}

		if (view.germanLanguage.getText() == "German") {
			view.connect4Help.setOnAction(e -> model.showHelp());

		}

		// switching background colors
		view.background1.setOnAction(e -> {
			view.root.setId(null);
			// view.root.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
			view.root.setStyle(" -fx-background-color: green");
		});

		view.background2.setOnAction(e -> {
			view.root.setId(null);
			// view.root.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, null, null)));
			view.root.setStyle(" -fx-background-color: lightblue");
		});

		view.background3.setOnAction(e -> {
			view.root.setId(null);
			// view.root.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
			view.root.setStyle(" -fx-background-color: white");
		});

		view.englishLanguage.setOnAction(e -> {
			changeLanguageENG();
		});




		view.germanLanguage.setOnAction(e -> {
			changeLanguageDE();
		});
	}


	public void changeLanguageENG() {
		view.gameRules.setText("Rules");
		view.gameLanguage.setText("Language");
		view.gameBackgrounds.setText("Backgrounds");
		view.restartGame.setText("Restart");
		view.exitGame.setText("Exit");
		view.germanLanguage.setText("German");
		view.englishLanguage.setText("English");
		view.connect4Rules.setText("Rules");
		view.connect4Help.setText("Help");
		view.background1.setText("green");
		view.background2.setText("light-blue");
		view.background3.setText("white");
		view.playAgain.setText("Play again");
		view.exit.setText("Exit");
	}

	private void changeLanguageDE() {
		view.gameRules.setText("Regeln");
		view.gameLanguage.setText("Sprache");
		view.gameBackgrounds.setText("Hintergrund");
		view.restartGame.setText("Neustart");
		view.exitGame.setText("Beenden");
		view.germanLanguage.setText("Deutsch");
		view.englishLanguage.setText("Englisch");
		view.connect4Rules.setText("Regeln");
		view.connect4Help.setText("Hilfe");
		view.background1.setText("grün");
		view.background2.setText("hellblau");
		view.background3.setText("weiss");
		view.playAgain.setText("Neues Spiel");
		view.exit.setText("Beenden");
		if (model.fullPlayingBoard() == true ) view.gameOverText.setText("Wow! Das Spiel endet unentschieden");
		if (model.getWinner() != null) view.gameOverText.setText("Wow! Spieler "+model.getWinner().toString()+" ist der Gewinner");
	}
	
	private void disableDiscPlacement() {
		for (int i = 0; i < model.COLUMNS; i++) {
			int column = i;

			view.getOverlay().get(column).setOnMouseClicked(e -> {
				// do nothing
			});
		}
	}
}
