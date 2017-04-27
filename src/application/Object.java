package application;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public abstract class Object extends ImageView{
	
	double height;
	double xPos;
	double yPos;
	double ySpeed;
	boolean toBeDestroy = false;
	MainController controller;
	Label nameLabel = null;
	Image image;
	
	// randomly generate object at certain position
	public Object(MainController controller){
		super();
		this.controller = controller;
		height = (Math.random() * 200 + 300) * MainController.heightRatio;
		xPos = GameManager.windowWidth;
		yPos = Math.random() * (GameManager.windowHeight - height);
		ySpeed = controller.ySpeed * ((Math.random() > 0) ? 1 : -1);
		this.setFitHeight(height);
		this.setX(xPos);
		this.setY(yPos);
		this.setPreserveRatio(true);
	}
	
	public void render(double deltaTime){
		double speed = controller.xspeed;
		xPos -= deltaTime * (speed + controller.goku.xspeed) / 1000;
		this.setX(xPos);
		if (checkCollide()) onColide();
		yPos += deltaTime * ySpeed / 1000;
		if (yPos < 0){
			ySpeed *= -1;
			yPos = 0;
		}
		if (yPos + height > GameManager.windowHeight){
			yPos = GameManager.windowHeight - height;
			ySpeed *= -1;
		}
		setY(yPos);
		updateNameLabel();
	}
	
	void setUpNameLabel(String name, Color color){
		this.nameLabel = new Label(name);
		this.nameLabel.setTextFill(color);
		this.nameLabel.setFont(new Font("Arial", 32));
		this.controller.mainPane.getChildren().add(this.nameLabel);
		this.nameLabel.setMinWidth(this.height * image.getWidth() / image.getHeight());
		this.nameLabel.setTextAlignment(TextAlignment.CENTER);
		this.nameLabel.setAlignment(Pos.CENTER);
		this.updateNameLabel();
	}
	
	public void updateNameLabel(){
		if (nameLabel != null){
			nameLabel.setTranslateX(xPos);
			nameLabel.setLayoutY(yPos - 36.0 * MainController.heightRatio);
		}
	}
	
	// check if collide with Goku
	public Boolean checkCollide(){
		return this.getBoundsInParent().intersects(controller.goku.getBoundsInParent());
	}
	
	// child class to implement when collide
	public void onColide(){
		toBeDestroy = true;
	}
	
	public void destroy(){
        controller.removeObj(Object.this);
	}
}
