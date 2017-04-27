package application;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

enum SuperSaiyanType{
	normal,
	super1,
	super2,
	super3,
	super4,
	superGod
}

public class SuperSaiyan extends Object {

	SuperSaiyanType type;
	
	public SuperSaiyan(MainController controller, SuperSaiyanType type) {
		super(controller);
		// TODO Auto-generated constructor stub
		this.type = type;
		image = loadImage(type);
		this.setImage(image);
		Color normal = Color.web("#FFFF00");
		Color god = Color.web("#51f7fd");
		switch (type){
			case super1: this.setUpNameLabel("Super Saiyan I", normal); break;
			case super2: this.setUpNameLabel("Super Saiyan II", normal); break;
			case super3: this.setUpNameLabel("Super Saiyan III", normal); break;
			case super4: this.setUpNameLabel("Super Saiyan IV", normal); break;
			case superGod: this.setUpNameLabel("SUPER SAIYAN GOD", god); break;
			default: break;
		}
		updateNameLabel();
	}

	// enable Goku to fire in 30 seconds
	@Override
	public void onColide() {
		// TODO Auto-generated method stub
		super.onColide();
		if (type == SuperSaiyanType.superGod || controller.goku.type != SuperSaiyanType.superGod) controller.goku.superMode(type);
	}

	static Image gokuImage = new Image(SuperSaiyan.class.getResource("/views/goku.png").toString());
	static Image super1Image = new Image(SuperSaiyan.class.getResource("/views/super1.png").toString());
	static Image super2Image = new Image(SuperSaiyan.class.getResource("/views/super2.png").toString());
	static Image super3Image = new Image(SuperSaiyan.class.getResource("/views/super3.png").toString());
	static Image super4Image = new Image(SuperSaiyan.class.getResource("/views/super4.png").toString());
	static Image superGodImage = new Image(SuperSaiyan.class.getResource("/views/superGod.png").toString());

	static Image loadImage(SuperSaiyanType type){
		switch (type){
			case normal: return gokuImage;
			case super1: return super1Image;
			case super2: return super2Image;
			case super3: return super3Image;
			case super4: return super4Image;
			case superGod: return superGodImage;
		}
		return null;
	}
}
