package application;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public abstract class Object extends ImageView{
	
	double height;
	double xPos;
	double yPos;
	double ySpeed;
	boolean toBeDestroy = false;
	MainController controller;
	
	// randomly generate object at certain position
	public Object(MainController controller){
		super();
		this.controller = controller;
		height = Math.random() * 200 + 300;
		xPos = 1600;
		yPos = Math.random() * (800 - height);
		ySpeed = controller.ySpeed * ((Math.random() > 0) ? 1 : -1);
		this.setFitHeight(height);
		this.setX(xPos);
		this.setY(yPos);
		this.setPreserveRatio(true);
	}
	
	public void render(double deltaTime){
		double speed = controller.goku.godTicks > 0 ? controller.xspeed * 3 : controller.xspeed;
		xPos -= deltaTime * speed / 1000;
		this.setX(xPos);
		if (checkCollide()) onColide();
		yPos += deltaTime * ySpeed / 1000;
		if (yPos < 0){
			ySpeed *= -1;
			yPos = 0;
		}
		if (yPos + height > 800){
			yPos = 800 - height;
			ySpeed *= -1;
		}
		setY(yPos);
	}
	
	// check if collide with Goku
	public Boolean checkCollide(){
		return (this.getBoundsInParent().intersects(controller.goku.getBoundsInParent()) && controller.goku.godTicks <= 0);
	}
	
	// child class to implement when collide
	public void onColide(){
//		destroy();
		toBeDestroy = true;
	}
	
	public void destroy(){
        controller.removeObj(Object.this);
	}
}
