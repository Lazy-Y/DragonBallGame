package application;

import java.util.LinkedList;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class MainController {
	
	@FXML AnchorPane mainPane;
	@FXML Label scoreLabel;
	@FXML Label hpLabel;
	@FXML Label killLabel;
	@FXML Label kameLabel;

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
	boolean endGame = false;

	@FXML private void initialize() {
		goku = new Goku(this);
		mainPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.SPACE){
                		goku.jumpAction();
                }
                else if (ke.getCode() == KeyCode.J){
                		goku.fireAction();
                }
                else if (ke.getCode() == KeyCode.Q){
                		endGame = true;
                }
            }
        });
		mainPane.setFocusTraversable(true);
		
		spawn();

		new Thread(){
			@Override
	        public void run() {
				long deltaTime = 20;
				try {
					while(hp > 0 && !endGame){
						Thread.sleep(deltaTime);
						Platform.runLater(()->{
							xspeed += deltaTime * acc / 1000;
							if (xspeed > maxSpeed) xspeed = maxSpeed;
							double speed = goku.godTicks > 0 ? xspeed * 3 : xspeed;
							score += speed * deltaTime / 1000;
							setScore((int)score);
							goku.render(deltaTime);
							if (objectList.isEmpty()) spawn();
							else{
								Object last = objectList.get(objectList.size() - 1);
								if (1600 - (last.xPos + last.getFitWidth()) > spacing) spawn();
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
							maxSpeed += 0.1;
							ySpeed += 0.02;
							if ((goku.godTicks > 0 || goku.superTicks > 0) && goku.coolingTicks <= 0) kameLabel.setTextFill(Color.web("#00FF00"));
							else kameLabel.setTextFill(Color.web("#FF0000"));
						});
					} 
				}
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Platform.runLater(()->{
					GameManager.addRecord(score, killCount);
					GameManager.endGame();
				});
	        }
		}.start();
	}
	
	void setScore(int score){
		scoreLabel.setText("score: " + score);
	}
	
	void spawn(){
		Object obj = null;
		double rand = Math.random();
		if (rand < 0.7) obj = new Block(this);
		else if (rand < 0.85) obj = new Monster(this);
		else if (rand < 0.95) obj = new SuperSaiyan(this);
		else obj = new God(this);
		objectList.add(objectList.size(), obj);
		mainPane.getChildren().add(obj);
	}
	
	void removeObj(Object obj){
		objectList.remove(obj);
		mainPane.getChildren().remove(obj);
	}
	
	void changeHP(int delta){
		hp += delta;
		hpLabel.setText("HP: " + hp + "/100");
	}
	
	void kill(){
		killCount++;
		killLabel.setText("kill: " + killCount);
		score += 2000;
	}
}
