package application;

import java.util.LinkedList;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class MainController {
	
	@FXML AnchorPane mainPane;
	@FXML Label scoreLabel;
	@FXML Label hpLabel;
	@FXML Label killLabel;
	@FXML ImageView kameIndicator;
	@FXML Label hintLabel;

	Goku goku;
	LinkedList<Object> objectList = new LinkedList<Object>(); 
	double xspeed = 0;
	double maxSpeed = 500;
	double acc = 500;
	double spacing = 500;
	int hp = 100;
	int killCount = 0;
	int score = 0;
	double ySpeed = 0;
	double hintDisplayTime = 5000;
	boolean pause = false;
	AnimationTimer mainAnimation;

	@FXML private void initialize() {
		goku = new Goku(this);
		mainPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.SPACE && !pause){
                		goku.jumpAction();
                }
                else if (ke.getCode() == KeyCode.J && !pause){
                		goku.fireAction();
                }
                else if (ke.getCode() == KeyCode.Q){
            			endGame();
	            }
	            else if (ke.getCode() == KeyCode.P){
	            		pause = true;
	            		hintLabel.setText("Press C to continue");
	            		hintLabel.setVisible(true);
	            }
	            else if (ke.getCode() == KeyCode.C){
	            		pause = false;
	            		hintLabel.setVisible(false);
	            }
            }
        });
		mainPane.setFocusTraversable(true);
		
		spawn();
		hintLabel.setText("Press space to jump\nPress J to fire\nPress Q to quit\nPress P to pause");

		mainAnimation = new AnimationTimer(){

			long prev = 0;
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if (prev == 0) prev = now;
				long deltaTime = (now - prev) / 1000000;
				prev = now;
				if (pause) return;
				if (hintDisplayTime > 0){
					hintDisplayTime -= deltaTime;
					if (hintDisplayTime <= 0){
						hintLabel.setVisible(false);
					}
				}
				xspeed += deltaTime * acc / 1000;
				if (xspeed > maxSpeed) xspeed = maxSpeed;
				double speed = xspeed;
				score += (speed + Math.max(goku.xspeed, 0)) * deltaTime / 1000;
				setScore((int)score);
				goku.render(deltaTime);
				if (objectList.isEmpty()) spawn();
				else{
					Object last = objectList.get(objectList.size() - 1);
					if (1600 - (last.xPos + last.getFitHeight()) > spacing) spawn();
				}
				Object first = objectList.getFirst();
				if (first.xPos < -1200) removeObj(first);
				for (Object obj : objectList) obj.render(deltaTime);
				for (int i = 0; i < objectList.size(); i++){
					Object obj = objectList.get(i);
					if (obj.toBeDestroy) {
						obj.destroy();
						i--;
					}
				}
				maxSpeed += 0.15;
				ySpeed += 0.02;
				if (goku.coolingTicks <= 0) kameIndicator.setBlendMode(BlendMode.SRC_OVER);
				else kameIndicator.setBlendMode(BlendMode.MULTIPLY);
			}
		};
		mainAnimation.start();
	}
	
	void setScore(int score){
		scoreLabel.setText("score: " + score);
	}
	
	void spawn(){
		Object obj = null;
		double rand = Math.random();
		if (rand < 0.7) obj = new Block(this);
		else if (rand < 0.9) obj = new Monster(this);
		else if (rand < 0.95) obj = new SuperSaiyan(this, SuperSaiyanType.super1);
		else if (rand < 0.975) obj = new SuperSaiyan(this, SuperSaiyanType.super2);
		else if (rand < 0.99) obj = new SuperSaiyan(this, SuperSaiyanType.super3);
		else if (rand < 0.997) obj = new SuperSaiyan(this, SuperSaiyanType.super4);
		else obj = new SuperSaiyan(this, SuperSaiyanType.superGod);

//		if (rand < 0.2) obj = new Block(this);
//		else if (rand < 0.3) obj = new Monster(this);
//		else if (rand < 0.4) obj = new SuperSaiyan(this, SuperSaiyanType.super1);
//		else if (rand < 0.5) obj = new SuperSaiyan(this, SuperSaiyanType.super2);
//		else if (rand < 0.6) obj = new SuperSaiyan(this, SuperSaiyanType.super3);
//		else if (rand < 0.7) obj = new SuperSaiyan(this, SuperSaiyanType.super4);
//		else obj = new SuperSaiyan(this, SuperSaiyanType.superGod);
		objectList.add(objectList.size(), obj);
		mainPane.getChildren().add(obj);
	}
	
	void removeObj(Object obj){
		if (obj.nameLabel != null) mainPane.getChildren().remove(obj.nameLabel);
		objectList.remove(obj);
		mainPane.getChildren().remove(obj);
	}
	
	void endGame(){
		mainAnimation.stop();
		
		GameManager.addRecord(score, killCount);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Great Job, Goku!");
		alert.setHeaderText(null);
		alert.setContentText("You killed " + killCount + " enemies and earned " + score + " points!");
		
		Platform.runLater(()->{
			alert.showAndWait();
			GameManager.endGame();
		});
	}
	
	void changeHP(int delta){
		hp += delta;
		hpLabel.setText("HP: " + hp + "/100");
		if (hp <= 0){
			endGame();
		}
	}
	
	void kill(){
		GameManager.playMusic(Monster.beatMonsterSound);
		killCount++;
		killLabel.setText("kill: " + killCount);
		score += 2000;
	}
}
