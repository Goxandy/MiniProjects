package ConnectFour;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ConnectFour_Controller {
	private final ConnectFour_Model model;
	private final ConnectFour_View view;

	public ConnectFour_Controller(ConnectFour_Model model, ConnectFour_View view) {
		this.model = model;
		this.view = view;

		view.startBtn.setOnAction( e -> {
			if (view.size.getValue() != null) model.setBoardSize((String) view.size.getValue());
			view.updateScene();
			view.changeScene(view.inGameScene);
			handleGameAction();
		});

		view.playAgain.setOnAction(e -> {
			view.discPane.getChildren().removeAll(view.discsToRemove);
			model.resetDiscBoard();
			view.changeScene(view.inGameScene);
		});

		view.exit.setOnAction(e -> {
			view.stop();
		});

	}

	private void handleGameAction(){
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

		view.animation.setOnFinished( e -> {
			final Object lock = new Object();
			if (model.getWinner() != null){
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
	}
}
