package application;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyValue;
import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Goku extends ImageView{
	
	void setGokuImage(){
		Image image = new Image("views/goku.png");
		this.setImage(image);
	}
	// build the player's character
	public Goku(MainController controller){
		super();
		setGokuImage();
		this.setFitHeight(100);
		this.setPreserveRatio(true);
		this.controller = controller;
		this.mainPane = controller.mainPane;
		mainPane.getChildren().add(this);
		this.setX(50);
		this.setY(pos);
	}
	
	void render(double deltaTime){
		yspeed += gravity * deltaTime / 1000;
		pos += yspeed * deltaTime / 1000;
		if (pos > 680){
			pos = 680;
			yspeed = 0;
		}
		if (pos < 0){
			pos = 0;
			yspeed = 0;
		}
		setY(pos);
		
		if (superTicks > 0){
			superTicks -= deltaTime;
			if (superTicks <= 0) setGokuImage();
		}
		
		if (godTicks > 0){
			godTicks -= deltaTime;
			if (godTicks <= 0) setGokuImage();
		}
		
		if (kame != null) kame.render(deltaTime);
		if (coolingTicks > 0) coolingTicks -= deltaTime;
	}
	 
	void jumpAction(){
		yspeed -= 1000;
	}

	void fireAction(){
		if (godTicks > 0 || superTicks > 0){
			if (coolingTicks <= 0){
				kame = new Kame(controller);
				if (godTicks > 0) coolingTicks = 400;
				else if (superTicks > 0) coolingTicks = 1000;
			}
		}
	}
	
	void superMode(){
		superTicks = 1000 * 10;
		godTicks = 0;
		this.setImage(new Image("views/super.png"));
	}
	
	void godMode(){
		godTicks = 1000 * 5;
		superTicks = 0;
		this.setImage(new Image("views/god.png"));
	}
	
	double superTicks = 0;
	double godTicks = 0;
	double coolingTicks = 0;
	Kame kame = null;
	
	MainController controller;
	AnchorPane mainPane;
	double yspeed = 0;
	double gravity = 2500;
	double pos = 350;
}
