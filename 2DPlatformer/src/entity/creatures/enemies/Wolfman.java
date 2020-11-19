package entity.creatures.enemies;


import java.awt.Rectangle;

import assets.Assets;
import entity.Entity;
import entity.creatures.Creature;
import gameState.GameStateManager;
import gameState.LevelState;
import gfx.AnimatedTexture;
import tiles.TileMap;

public class Wolfman extends Creature {

	private static Rectangle bounds = new Rectangle(0,0,15,31);

	public Wolfman(GameStateManager gsm, int x, int y) {
		super(gsm, new AnimatedTexture(Assets.WOLFMAN), x, y, bounds, true);

		maxHealth = 2;
		health = maxHealth;
		speed = 0.75;
		affectedByGravity = true;
		localGravityScale = 1;
		jumpPower = 0;
	}

	private void movementLogic() {
		if(!falling && !jumping) {
			TileMap map = ((LevelState)gsm.getCurrentState()).getTileMap();
			if(facingRight) {

				int tileX = map.getTileX(getTileStandingOn(Entity.Direction.RIGHT));
				int tileY = map.getTileY(getTileStandingOn(Entity.Direction.RIGHT));
				if(map.getTileAtIndex(tileX + 1, tileY).isSolid()
						&& map.getTileAtIndex(tileX, tileY).isSolid()) {
					if(!checkMapCollision((int)(x + speed), (int)y))
						facingRight = false;
					else
						moveX(Entity.Direction.RIGHT, speed);
				} else {
					facingRight = false;
				}


			} else {
				int tileX = map.getTileX(getTileStandingOn(Entity.Direction.LEFT));
				int tileY = map.getTileY(getTileStandingOn(Entity.Direction.LEFT));
				if(map.getTileAtIndex(tileX - 1, tileY).isSolid()
						&& map.getTileAtIndex(tileX, tileY).isSolid()) {
					if(!checkMapCollision((int)(x - speed), (int)y))
						facingRight = true;
					else
						moveX(Entity.Direction.LEFT, speed);
				} else {
					facingRight = true;
				}
			}
		}
	}

	@Override
	public void updateLogic() {
		if(((LevelState)gsm.getCurrentState()).getEntityManager().checkPlayerCollision(this)) {
			((LevelState)gsm.getCurrentState()).getEntityManager().getPlayer().hurt(this);
		}
		movementLogic();
		tex.update();
	}
}
