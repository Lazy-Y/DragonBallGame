package application;
	
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


public class GameManager extends Application {
	
	private static AnchorPane beginPane;
	private static BeginController beginController;
	private static AnchorPane mainPane;
	private static AnchorPane recordPane;
	private static BorderPane pane;
	private static Stage stage;
	private static MediaPlayer mediaPlayer;
	static String playerName = "";
	
	public static Stage getStage(){
		return stage;
	}
	
	// begin game
	public static void begin(){
        FXMLLoader mainLoader = new FXMLLoader();
        mainLoader.setLocation(GameManager.class.getResource("MainPane.fxml"));
        try {
			playerName = beginController.username.getText();
			mainPane = (AnchorPane) mainLoader.load();
			pane.setCenter(mainPane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void reset(){

		FXMLLoader beginLoader = new FXMLLoader();
		beginLoader.setLocation(GameManager.class.getResource("BeginPane.fxml"));
        try {
			beginPane = (AnchorPane) beginLoader.load();
	        pane.setCenter(beginPane);
	        beginController = (BeginController) beginLoader.getController();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        		
	}
	
	public static void endGame(){
        FXMLLoader recordLoader = new FXMLLoader();
        recordLoader.setLocation(GameManager.class.getResource("RecordPane.fxml"));
        try {
			recordPane = (AnchorPane) recordLoader.load();
			pane.setCenter(recordPane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			String path = "bgmusic.mp3";
			Media media = new Media(GameManager.class.getResource(path).toString());
			 
			mediaPlayer = new MediaPlayer(media);
			mediaPlayer.setAutoPlay(true);
			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			
			pane = new BorderPane();
			 
			reset();
			
			Scene scene = new Scene(pane,400,400);
			primaryStage.setScene(scene);
			primaryStage.setWidth(1600);
			primaryStage.setHeight(800);
			primaryStage.setResizable(false);
			primaryStage.show();
			stage = primaryStage;
			pane.setFocusTraversable(true);
			beginController.bindBackgroundImage();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void addRecord(int score, int kill){
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			String data = playerName + "," + String.valueOf(score) + "," + String.valueOf(kill) + "\n";
			File file = new File("record.csv");
			// if file doesnt exists, then create it
			if (!file.exists()) file.createNewFile();
			// true = append file
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			bw.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static ObservableList<RecordData> getRecords(){
        ObservableList<RecordData> data = FXCollections.observableArrayList();
        String csvFile = "record.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] lineData = line.split(cvsSplitBy);
                data.add(new RecordData(lineData[0], Integer.valueOf(lineData[1]), Integer.valueOf(lineData[2])));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }   
        return data;
	}
}
