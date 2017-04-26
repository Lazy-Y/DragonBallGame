package application;

import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaPlayer;

public class Kame extends ImageView {
	
	private Goku goku;
	private AnchorPane pane;
	private double fireTicks = 300;
	private List<Object> objectList;
	private MainController controller;
	static Image kameImage = new Image("views/kame.png");
	static MediaPlayer kameSound = GameManager.loadMusic("kame.mp3");
	
	public Kame(MainController controller){
		super();
		GameManager.playMusic(kameSound);
		this.goku = controller.goku;
		this.pane = controller.mainPane;
		this.controller = controller;
		this.objectList = controller.objectList;
		pane.getChildren().add(this);
		setImage(kameImage);
		switch (goku.type){
			case normal:
				this.setFitHeight(10);
				break;
			case super3:
				this.setFitHeight(40);
				break;
			case super4:
				this.setFitHeight(50);
				break;
			case superGod:
				this.setFitHeight(60);
				break;
			default:
				this.setFitHeight(20);
				break;
		}
		this.setPreserveRatio(true);
		this.setX(goku.getX() + 100);
		this.setY(goku.getY() + goku.getFitHeight() / 2);
	}
	
	void render(double deltaTime){
		this.setX(goku.getX() + 100);
		this.setY(goku.getY() + goku.getFitHeight() / 2);
		fireTicks -= deltaTime;
		if (fireTicks <= 0) destroy();
		Object obj_to_destroy = null;
		for (Object obj : objectList){
			if (obj.getBoundsInParent().intersects(this.getBoundsInParent())){
				if (obj instanceof Monster) controller.kill();
				obj_to_destroy = obj;
			}
		}
		if (obj_to_destroy != null){
			obj_to_destroy.destroy();
			if (obj_to_destroy instanceof Block) GameManager.playMusic(Block.beatWallSound);
		}
	}
	
	void destroy(){
		pane.getChildren().remove(this);
		goku.kame = null;
	}
	
}
