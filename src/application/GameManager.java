package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class GameManager extends Application {

	private static AnchorPane beginPane;
	private static BeginController beginController;
	private static AnchorPane mainPane;
	private static AnchorPane infoPane;
	private static AnchorPane recordPane;
	private static BorderPane pane;
	private static Stage stage;
	private static MediaPlayer dragonBallPlayer = loadBgMusic("DragonBall.mp3");
	private static MediaPlayer dragonBallZPlayer = loadBgMusic("DragonBallZ.mp3");
	private static MediaPlayer dragonBallSuperPlayer = loadBgMusic("DragonBallSuper.mp3");
	private static MediaPlayer dragonBallGTPlayer = loadBgMusic("DragonBallGT.mp3");
	static String playerName = "";

	public static Stage getStage() {
		return stage;
	}

	// begin game
	public static void begin() {
		FXMLLoader mainLoader = new FXMLLoader();
		mainLoader.setLocation(GameManager.class.getResource("MainPane.fxml"));
		try {
			dragonBallGTPlayer.stop();
			dragonBallZPlayer.stop();
			dragonBallSuperPlayer.play();
			playerName = beginController.username.getText();
			mainPane = (AnchorPane) mainLoader.load();
			pane.setCenter(mainPane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void reset() {
		FXMLLoader beginLoader = new FXMLLoader();
		beginLoader.setLocation(GameManager.class.getResource("BeginPane.fxml"));
		try {
			dragonBallPlayer.stop();
			dragonBallGTPlayer.stop();
			dragonBallZPlayer.play();
			beginPane = (AnchorPane) beginLoader.load();
			pane.setCenter(beginPane);
			beginController = (BeginController) beginLoader.getController();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void showInfo() {
		FXMLLoader infoLoader = new FXMLLoader();
		infoLoader.setLocation(GameManager.class.getResource("InfoPane.fxml"));
		try {
			dragonBallZPlayer.stop();
			dragonBallPlayer.play();
			infoPane = (AnchorPane) infoLoader.load();
			pane.setCenter(infoPane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void endGame() {
		FXMLLoader recordLoader = new FXMLLoader();
		recordLoader.setLocation(GameManager.class.getResource("RecordPane.fxml"));
		try {
			dragonBallSuperPlayer.stop();
			dragonBallGTPlayer.play();
			recordPane = (AnchorPane) recordLoader.load();
			pane.setCenter(recordPane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static MediaPlayer loadMusic(String name) {
		Media media = new Media(GameManager.class.getResource(name).toString());
		MediaPlayer player = new MediaPlayer(media);
		player.setCycleCount(1);
		player.setAutoPlay(false);
		if (name.equals("jump.mp3")) {
			player.setVolume(0.4);
		}
		return player;
	}

	static MediaPlayer loadBgMusic(String name) {
		String path = GameManager.class.getResource(name).toString();
		Media media = new Media(path);
		MediaPlayer player = new MediaPlayer(media);
		player.setCycleCount(MediaPlayer.INDEFINITE);
		player.setAutoPlay(false);
		player.setVolume(0.4);
		return player;
	}

	static void playMusic(MediaPlayer player) {
		player.stop();
		Platform.runLater(() -> {
			player.stop();
			player.play();
		});
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			pane = new BorderPane();
			reset();
			Scene scene = new Scene(pane, 400, 400);
			primaryStage.setScene(scene);
			primaryStage.setWidth(1600);
			primaryStage.setHeight(800);
			primaryStage.setResizable(false);
			primaryStage.show();
			stage = primaryStage;
			stage.setTitle("Jumping Goku");
			pane.setFocusTraversable(true);
			beginController.bindBackgroundImage();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static ArrayList<Image> monsterImage = new ArrayList<Image>();
	static String[] monsterName = { "Cell", "Broli", "Buu", "Frieza", "Zamasu", "Black Goku", "One Star Dragon",
			"Frieza", "Super Baby", "Baby", "Cooler", "Buu", "Buu" };

	public static void main(String[] args) {
		for (int i = 0; i < 13; i++) {
			String path = GameManager.class.getResource("/views/monster" + i + ".png").toString();
			monsterImage.add(new Image(path));
		}

		launch(args);
	}

	public static void addRecord(int score, int kill) {
		if (playerName == null || playerName.length() == 0) return;
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			String data = playerName + "," + String.valueOf(score) + "," + String.valueOf(kill) + "\n";
			File file = new File("record.csv");
			// if file doesnt exists, then create it
			if (!file.exists())
				file.createNewFile();
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

	public static ObservableList<RecordData> getRecords() {
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
