package application;

import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;

public class Block extends Object{

	static Image blockImage = new Image(SuperSaiyan.class.getResource("/views/block.png").toString());
	static MediaPlayer hitWallSound = GameManager.loadMusic("hitWall.mp3");
	static MediaPlayer beatWallSound = GameManager.loadMusic("beatWall.mp3");
	
	public Block(MainController controller) {
		super(controller);
		// TODO Auto-generated constructor stub
		this.setImage(blockImage);
	}

	// slow down Goku
	@Override
	public void onColide() {
		if (controller.goku.type == SuperSaiyanType.super4) return;
		super.onColide();
		if (controller.goku.type == SuperSaiyanType.super3 || controller.goku.type == SuperSaiyanType.superGod){
			GameManager.playMusic(beatWallSound);
		}
		else if (controller.xspeed > 0){
			controller.xspeed = -500;
			controller.changeHP(-5);
			GameManager.playMusic(hitWallSound);
		}
	}

}
