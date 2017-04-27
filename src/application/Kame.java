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
	static Image kameImage = new Image(SuperSaiyan.class.getResource("/views/kame.png").toString());
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
		double baseHeight = 50;
		switch (goku.type){
			case normal:
				this.setFitHeight(1 * baseHeight);
				break;
			case super3:
				this.setFitHeight(4 * baseHeight);
				break;
			case super4:
				this.setFitHeight(5 * baseHeight);
				break;
			case superGod:
				this.setFitHeight(6 * baseHeight);
				break;
			default:
				this.setFitHeight(2 * baseHeight);
				break;
		}
		this.setPreserveRatio(true);
		this.setX(goku.getX() + goku.getFitHeight() * 3 / 4);
		this.setY(goku.getY() + goku.getFitHeight() / 4);
	}
	
	void render(double deltaTime){
		this.setX(goku.getX() + goku.getFitHeight() * 3 / 4);
		this.setY(goku.getY() + goku.getFitHeight() / 4);
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
