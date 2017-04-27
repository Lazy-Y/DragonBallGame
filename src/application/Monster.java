package application;

import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

public class Monster extends Object {
	
	static MediaPlayer hitMonsterSound = GameManager.loadMusic("hitMonster.mp3");
	static MediaPlayer beatMonsterSound = GameManager.loadMusic("beatMonster.mp3");
	
	public Monster(MainController controller) {
		super(controller);
		// TODO Auto-generated constructor stub
		int randInt = (int)(Math.random() * 13);
		if (randInt > 12) randInt = 12;
		image = GameManager.monsterImage.get(randInt);
		this.setImage(image);
		this.setUpNameLabel(GameManager.monsterName[randInt], Color.web("#cd0538"));
	}

	// kill Goku end game
	@Override
	public void onColide() {
		// TODO Auto-generated method stub
		if (controller.goku.type == SuperSaiyanType.super4) return;
		super.onColide();
		if (controller.goku.type == SuperSaiyanType.super3 || controller.goku.type == SuperSaiyanType.superGod){
			controller.kill();
		}
		else if (controller.xspeed > 0){
			controller.changeHP(-20);
			controller.xspeed = -1000;
			GameManager.playMusic(hitMonsterSound);
		}
	}
}
