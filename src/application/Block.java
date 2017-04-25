package application;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class Block extends Object{

	public Block(MainController controller) {
		super(controller);
		// TODO Auto-generated constructor stub
		Image image = new Image("views/block.png");
		this.setImage(image);
	}

	// slow down Goku
	@Override
	public void onColide() {
		super.onColide();
		if (controller.xspeed > 0){
			controller.xspeed = -500;
			controller.changeHP(-5);
		}
	}

}
