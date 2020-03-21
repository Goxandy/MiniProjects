package ConnectFour;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ConnectFour_Controller {
	private final ConnectFour_Model model;
	private final ConnectFour_View view;
	
	public ConnectFour_Controller(ConnectFour_Model model, ConnectFour_View view) {
		this.model = model;
		this.view = view;
		
		// Display the overlay so the user know at which column he's aiming at
		for (Rectangle r : view.getOverlay()) {
			r.setOnMouseEntered( e -> r.setFill(Color.rgb(200, 200, 50, 0.3)));
			r.setOnMouseExited( e-> r.setFill(Color.TRANSPARENT));
		}	
		
		// TODO Set the action to place a disc in the grid
			
		for (int i = 0; i < model.COLUMNS; i++) {
			int column = i;
			// TODO Set the action to place a disc in the grid
			view.getOverlay().get(column).setOnMouseClicked(e -> {
				model.makeMove(column);
				view.placeDisc();
				if (model.getWinner() != null) {
				System.out.println(model.getWinner().toString());
				} else { 
					System.out.println("Winner is null");
				}
			});
		}
	}
}
