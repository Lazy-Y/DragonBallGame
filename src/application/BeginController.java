package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class BeginController {

	@FXML ImageView background;
	
	@FXML TextField username;
	
	@FXML public void startGame(ActionEvent action){
		GameManager.begin();
	}
	
	@FXML public void showInfo(ActionEvent action){
		GameManager.showInfo();
	}
	
	@FXML
	private void initialize() {
		username.setFocusTraversable(false);
	}

	public void bindBackgroundImage(){
		System.out.println(background);
		Stage stage = GameManager.getStage();
		background.fitWidthProperty().bind(stage.widthProperty());
	}
}
