package application;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;

public class Goku extends ImageView{
	
	SuperSaiyanType type;
	static MediaPlayer super1Sound = GameManager.loadMusic("saiyan1.mp3");
	static MediaPlayer super2Sound = GameManager.loadMusic("saiyan2.mp3");
	static MediaPlayer super3Sound = GameManager.loadMusic("saiyan3.mp3");
	static MediaPlayer super4Sound = GameManager.loadMusic("saiyan4.mp3");
	static MediaPlayer superGodSound = GameManager.loadMusic("god.mp3");
	static MediaPlayer jumpSound = GameManager.loadMusic("jump.mp3");
	
	void setGokuImage(){
		this.setImage(SuperSaiyan.gokuImage);
	}
	// build the player's character
	public Goku(MainController controller){
		super();
		type = SuperSaiyanType.normal;
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
		double yPosMax = 680;
		switch (type){
			case normal: yPosMax = 680; break;
			case super1: yPosMax = 660; break;
			case super2: yPosMax = 630; break;
			case super3: yPosMax = 580; break;
			case super4: yPosMax = 480; break;
			case superGod: yPosMax = 380; break;
		}
		if (pos > yPosMax){
			pos = yPosMax;
			yspeed = 0;
		}
		if (pos < 0){
			pos = 0;
			yspeed = 0;
		}
		setY(pos);
		
		if (superTicks > 0){
			superTicks -= deltaTime;
			if (superTicks <= 0){
				type = SuperSaiyanType.normal;
				this.setFitHeight(100);
				setGokuImage();
				xspeed = 0;
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
	static String superSaiyanIIIInfo = "- Destroy everything if hitten\n- Large kame\n- Medium cooling time\n- Mediu speed";
	static String superSaiyanIVInfo =  "- Pass through everything\n- X-large kame\n- Short cooling time\n- Fast speed";
	static String superSaiyanGodInfo = "- Destroy everything if hitten\n- Mega-large kame\n- Short cooling time\n- Fastest speed";
	void superMode(SuperSaiyanType type){
		controller.hintLabel.setVisible(true);
		controller.hintDisplayTime = 3000;
		controller.goku.coolingTicks = 0;
		controller.goku.superTicks = 7000;
		switch (type){
			case super1:
				GameManager.playMusic(super1Sound);
				xspeed = 0;
				this.setFitHeight(120);
				controller.hintLabel.setText("Super Saiyan I!\n" + superSaiyanIInfo);
				break;
			case super2:
				GameManager.playMusic(super2Sound);
				xspeed = -400;
				this.setFitHeight(150);
				controller.hintLabel.setText("Super Saiyan II!\n" + superSaiyanIIInfo);
				break;
			case super3:
				GameManager.playMusic(super3Sound);
				xspeed = 0;
				this.setFitHeight(200);
				controller.hintLabel.setText("Super Saiyan III!\n" + superSaiyanIIIInfo);
				break;
			case super4:
				GameManager.playMusic(super4Sound);
				xspeed = 300;
				this.setFitHeight(300);
				controller.hintLabel.setText("Super Saiyan IV!\n" + superSaiyanIVInfo);
				break;
			case superGod:
				GameManager.playMusic(super4Sound);
				GameManager.playMusic(superGodSound);
				xspeed = 800;
				this.setFitHeight(400);
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
	double jumpHeight = 1000;
	double yspeed = 0;
	double gravity = 2500;
	double pos = 350;
	double xspeed = 0;
}
