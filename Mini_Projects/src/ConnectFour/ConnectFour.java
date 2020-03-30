package ConnectFour;
import javafx.application.Application;
import javafx.stage.Stage;

public class ConnectFour extends Application {
	private ConnectFour_Model model;
	private  ConnectFour_View view;
	private  ConnectFour_Controller controller;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		model = new ConnectFour_Model();
		view = new ConnectFour_View(stage, model);
		controller = new ConnectFour_Controller(model, view);
		
		view.start();
		
	}

}
