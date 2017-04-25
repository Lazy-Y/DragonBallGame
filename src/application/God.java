package application;

import javafx.scene.image.Image;

public class God extends Object {

	public God(MainController controller) {
		super(controller);
		// TODO Auto-generated constructor stub
		Image image = new Image("views/god.png");
		this.setImage(image);
	}

	// Goku becomes super saiyan god!!!
	@Override
	public void onColide() {
		// TODO Auto-generated method stub
//		controller.goku.setImage(this.getImage());
		super.onColide();
		controller.goku.godMode();
	}

}
