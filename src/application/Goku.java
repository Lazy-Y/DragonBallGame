package application;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Goku extends ImageView{
	
	SuperSaiyanType type;
	static MediaPlayer super1Sound = GameManager.loadMusic("saiyan1.mp3");
	static MediaPlayer super2Sound = GameManager.loadMusic("saiyan2.mp3");
	static MediaPlayer super3Sound = GameManager.loadMusic("saiyan3.mp3");
	static MediaPlayer super4Sound = GameManager.loadMusic("saiyan4.mp3");
	static MediaPlayer superGodSound = GameManager.loadMusic("god.mp3");
	static MediaPlayer jumpSound = GameManager.loadMusic("jump.mp3");
	
	static double normalHeight = 100.0 * MainController.heightRatio;
	static double superIHeight = 120.0 * MainController.heightRatio;
	static double superIIHeight = 150.0 * MainController.heightRatio;
	static double superIIIHeight = 200.0 * MainController.heightRatio;
	static double superIVHeight = 300.0 * MainController.heightRatio;
	static double superGodHeight = 400.0 * MainController.heightRatio;
	
	FadeTransition flashAnimation = null;
	
	void setGokuImage(){
		this.setImage(SuperSaiyan.gokuImage);
	}
	// build the player's character
	public Goku(MainController controller){
		super();
		type = SuperSaiyanType.normal;
		setGokuImage();
		this.setFitHeight(normalHeight);
		this.setPreserveRatio(true);
		this.controller = controller;
		this.mainPane = controller.mainPane;
		mainPane.getChildren().add(this);
		this.setX(xPos);
		this.setY(yPos);
	}
	
	void render(double deltaTime){
		yspeed += gravity * deltaTime / 1000;
		yPos += yspeed * deltaTime / 1000;
		double yPosMax = 0;
		switch (type){
			case normal: yPosMax = GameManager.windowHeight - normalHeight - 20 * MainController.heightRatio; break;
			case super1: yPosMax = GameManager.windowHeight - superIHeight - 20 * MainController.heightRatio; break;
			case super2: yPosMax = GameManager.windowHeight - superIIHeight - 20 * MainController.heightRatio; break;
			case super3: yPosMax = GameManager.windowHeight - superIIIHeight - 20 * MainController.heightRatio; break;
			case super4: yPosMax = GameManager.windowHeight - superIVHeight - 20 * MainController.heightRatio; break;
			case superGod: yPosMax = GameManager.windowHeight - superGodHeight - 20 * MainController.heightRatio; break;
		}
		if (yPos > yPosMax){
			yPos = yPosMax;
			yspeed = 0;
		}
		if (yPos < 0){
			yPos = 0;
			yspeed = 0;
		}
		setY(yPos);
		
		if (superTicks > 0){
			superTicks -= deltaTime;
			if (superTicks < 2000 && flashAnimation == null){
				flashAnimation = new FadeTransition(Duration.millis(250), this);
				flashAnimation.setFromValue(1.0);
				flashAnimation.setToValue(0.3);
				flashAnimation.setCycleCount(Timeline.INDEFINITE);
				flashAnimation.setAutoReverse(true);
				flashAnimation.play();
			}
			if (superTicks <= 0){
				type = SuperSaiyanType.normal;
				this.setFitHeight(normalHeight);
				setGokuImage();
				xspeed = 0;
				flashAnimation.stop();
				flashAnimation = null;
			}
		}
		
		if (kame != null) kame.render(deltaTime);
		if (coolingTicks > 0) coolingTicks -= deltaTime;
	}
	 
	void jumpAction(){
		GameManager.playMusic(jumpSound);
		yspeed -= jumpHeight;
	}

	void fireAction(){
		if (coolingTicks <= 0){
			kame = new Kame(controller);
			switch (type){
				case normal:
					coolingTicks = 3000;
					break;
				case super1:
					coolingTicks = 1000;
					break;
				case super2:
					coolingTicks = 1000;
					break;
				case super3:
					coolingTicks = 1500;
					break;
				case super4:
					coolingTicks = 800;
					break;
				case superGod:
					coolingTicks = 400;
					break;
				default:
					coolingTicks = 1000;
					break;
			}
		}
	}
	
	static String childGokuInfo = "- Small kame\n- Long cooling time\n- Medium speed";
	static String superSaiyanIInfo = "- Medium kame\n- Medium cooling time\n- Medium speed";
	static String superSaiyanIIInfo = "- Medium kame\n- Medium cooling time\n- Slow speed";
	static String superSaiyanIIIInfo = "- Destroy everything if hit\n- Large kame\n- Medium cooling time\n- Mediu speed";
	static String superSaiyanIVInfo =  "- Pass through everything\n- X-large kame\n- Short cooling time\n- Fast speed";
	static String superSaiyanGodInfo = "- Destroy everything if hit\n- Mega-large kame\n- Short cooling time\n- Fastest speed";
	void superMode(SuperSaiyanType type){
		controller.hintLabel.setVisible(true);
		controller.hintDisplayTime = 3000;
		controller.goku.coolingTicks = 0;
		controller.goku.superTicks = 7000;
		if (flashAnimation != null){
			this.setOpacity(1);
			flashAnimation.stop();
			flashAnimation = null;
		}
		switch (type){
			case super1:
				GameManager.playMusic(super1Sound);
				xspeed = 0;
				this.setFitHeight(superIHeight);
				controller.hintLabel.setText("Super Saiyan I!\n" + superSaiyanIInfo);
				break;
			case super2:
				GameManager.playMusic(super2Sound);
				xspeed = - controller.xspeed * 0.5;
				this.setFitHeight(superIIHeight);
				controller.hintLabel.setText("Super Saiyan II!\n" + superSaiyanIIInfo);
				break;
			case super3:
				GameManager.playMusic(super3Sound);
				xspeed = 0;
				this.setFitHeight(superIIIHeight);
				controller.hintLabel.setText("Super Saiyan III!\n" + superSaiyanIIIInfo);
				break;
			case super4:
				GameManager.playMusic(super4Sound);
				xspeed = 300 * MainController.widthRatio;
				this.setFitHeight(superIVHeight);
				controller.hintLabel.setText("Super Saiyan IV!\n" + superSaiyanIVInfo);
				break;
			case superGod:
				GameManager.playMusic(super4Sound);
				GameManager.playMusic(superGodSound);
				xspeed = 800 * MainController.widthRatio;
				this.setFitHeight(superGodHeight);
				controller.hintLabel.setText("SUPER SAIYAN GOD!!!\n" + superSaiyanGodInfo);
			default:
				break;
		}
		this.type = type;
		this.setImage(SuperSaiyan.loadImage(type));
	}
	
	double superTicks = 0;
	double coolingTicks = 0;
	Kame kame = null;
	
	MainController controller;
	AnchorPane mainPane;
	double jumpHeight = 1000 * MainController.heightRatio;
	double yspeed = 0;
	double gravity = 2500 * MainController.heightRatio;
	double yPos = 350 * MainController.heightRatio;
	double xPos = 50 * MainController.widthRatio;
	double xspeed = 0;
}
