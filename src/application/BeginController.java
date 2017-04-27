package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class BeginController {

	@FXML ImageView background;
	@FXML TextField username;
	@FXML Button beginButton;
	@FXML Button infoButton;
	@FXML Label declarationLabel;
	
	@FXML public void startGame(ActionEvent action){
		GameManager.begin();
	}
	
	@FXML public void showInfo(ActionEvent action){
		GameManager.showInfo();
	}
	
	@FXML
	private void initialize() {
		username.setFocusTraversable(false);
		background.setFitWidth(GameManager.windowWidth);
		username.setLayoutX(256.0 * MainController.widthRatio);
		username.setLayoutY(436.0 * MainController.heightRatio);
		username.setPrefWidth(410.0 * MainController.widthRatio);
		username.setPrefHeight(60.0 * MainController.heightRatio);

		beginButton.setLayoutX(256.0 * MainController.widthRatio);
		beginButton.setLayoutY(510.0 * MainController.heightRatio);
		beginButton.setPrefWidth(200.0 * MainController.widthRatio);
		beginButton.setPrefHeight(60.0 * MainController.heightRatio);

		infoButton.setLayoutX(466.0 * MainController.widthRatio);
		infoButton.setLayoutY(510.0 * MainController.heightRatio);
		infoButton.setPrefWidth(200.0 * MainController.widthRatio);
		infoButton.setPrefHeight(60.0 * MainController.heightRatio);

		declarationLabel.setLayoutX(20 * MainController.widthRatio);
		declarationLabel.setLayoutY(650 * MainController.heightRatio);
	}

	public void bindBackgroundImage(){
		Stage stage = GameManager.getStage();
		background.fitWidthProperty().bind(stage.widthProperty());
	}
}
