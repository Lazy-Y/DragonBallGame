package application;

import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Kame extends ImageView {
	
	private Goku goku;
	private AnchorPane pane;
	private double fireTicks = 300;
	private List<Object> objectList;
	private MainController controller;
	
	public Kame(MainController controller){
		super();
		this.goku = controller.goku;
		this.pane = controller.mainPane;
		this.controller = controller;
		this.objectList = controller.objectList;
		pane.getChildren().add(this);
		Image image = new Image("views/kame.png");
		setImage(image);
		if (goku.godTicks > 0) this.setFitHeight(60);
		else this.setFitHeight(20);
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
//				obj.destroy();
			}
		}
		if (obj_to_destroy != null) obj_to_destroy.destroy();
	}
	
	void destroy(){
		pane.getChildren().remove(this);
		goku.kame = null;
	}
	
}
