package application;

import javafx.scene.image.Image;

public class SuperSaiyan extends Object {

	enum type{
		
	}
	
	public SuperSaiyan(MainController controller) {
		super(controller);
		// TODO Auto-generated constructor stub
		Image image = new Image("views/super.png");
		this.setImage(image);
	}

	// enable Goku to fire in 30 seconds
	@Override
	public void onColide() {
		// TODO Auto-generated method stub
		super.onColide();
		controller.goku.superMode();
	}

}
