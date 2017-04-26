package application;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class InfoController {
	@FXML Label gokuChildLabel;
	@FXML Label superSaiyanILabel;
	@FXML Label superSaiyanIILabel;
	@FXML Label superSaiyanIIILabel;
	@FXML Label superSaiyanIVLabel;
	@FXML Label superSaiyanGodLabel;
	@FXML AnchorPane infoPane;
	@FXML private void initialize() {
		gokuChildLabel.setText(Goku.childGokuInfo);
		superSaiyanILabel.setText(Goku.superSaiyanIInfo);
		superSaiyanIILabel.setText(Goku.superSaiyanIIInfo);
		superSaiyanIIILabel.setText(Goku.superSaiyanIIInfo);
		superSaiyanIVLabel.setText(Goku.superSaiyanIVInfo);
		superSaiyanGodLabel.setText(Goku.superSaiyanGodInfo);
		infoPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.Q){
            			GameManager.reset();
	            }
            }
        });
	}
}
