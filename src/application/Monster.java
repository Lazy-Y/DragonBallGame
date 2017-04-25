package application;

import javafx.scene.image.Image;

public class Monster extends Object {
	
	public Monster(MainController controller) {
		super(controller);
		// TODO Auto-generated constructor stub
		int randInt = (int)(Math.random() * 6);
		if (randInt > 4) randInt = 4;
		Image image = new Image("views/monster" + randInt + ".png");
		this.setImage(image);
	}

	// kill Goku end game
	@Override
	public void onColide() {
		// TODO Auto-generated method stub
		super.onColide();
		if (controller.xspeed > 0){
			controller.changeHP(-20);
			controller.xspeed = -1000;
		}
	}
}
