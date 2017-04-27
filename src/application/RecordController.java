package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

public class RecordController implements Initializable {
	
	@FXML Button restartBtn;
	@FXML Button resetBtn;
	@FXML TableView<RecordData> table;
    @FXML TableColumn<RecordData, String> nameColumn;
    @FXML TableColumn<RecordData, Number> scoreColumn;
    @FXML TableColumn<RecordData, Number> killColumn;
    @FXML ImageView background;
	
	@FXML void restartAction(ActionEvent action){
		GameManager.begin();
	}
	
	@FXML void resetAction(ActionEvent action){
		GameManager.reset();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		background.setFitWidth(GameManager.windowWidth);
		
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().name);
        scoreColumn.setCellValueFactory(cellData -> cellData.getValue().score);
        killColumn.setCellValueFactory(cellData -> cellData.getValue().kill);

        ObservableList<RecordData> data = GameManager.getRecords();
        table.setItems(data);
        table.requestFocus();
        table.getSelectionModel().select(data.size()-1);
        table.getFocusModel().focus(data.size()-1);
        
        scoreColumn.setComparator(scoreColumn.getComparator().reversed());
        table.getSortOrder().add(scoreColumn);
	}
}
