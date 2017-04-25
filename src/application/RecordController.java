package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class RecordController implements Initializable {
	
	@FXML Button restartBtn;
	@FXML Button resetBtn;
	@FXML TableView<RecordData> table;
    @FXML TableColumn<RecordData, String> nameColumn;
    @FXML TableColumn<RecordData, Number> scoreColumn;
    @FXML TableColumn<RecordData, Number> killColumn;
	
	@FXML void restartAction(ActionEvent action){
		GameManager.begin();
	}
	
	@FXML void resetAction(ActionEvent action){
		GameManager.reset();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().name);
        scoreColumn.setCellValueFactory(cellData -> cellData.getValue().score);
        killColumn.setCellValueFactory(cellData -> cellData.getValue().kill);

        ObservableList<RecordData> data = GameManager.getRecords();
        
//        RecordData record = new RecordData("Lazy-Y", 100000, 100);
//        data.add(record);
        scoreColumn.setComparator(scoreColumn.getComparator().reversed());
        table.setItems(data);
        table.getSortOrder().add(scoreColumn);
	}
}
