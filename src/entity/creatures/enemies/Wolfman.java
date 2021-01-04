package entity.creatures.enemies;


import java.awt.Rectangle;

import assets.Assets;
import entity.Entity;
import entity.creatures.Creature;
import gameState.GameStateManager;
import gameState.LevelState;
import gfx.AnimatedTexture;
import tiles.Tile;
import tiles.TileMap;

public class Wolfman extends Creature {

	private static Rectangle bounds = new Rectangle(0,0,15,31);
	TileMap map;

	public Wolfman(GameStateManager gsm, int x, int y, boolean sideFacing) {
		super(gsm, new AnimatedTexture(Assets.WOLFMAN), x, y, bounds, true);

		maxHealth = 2;
		health = maxHealth;
		speed = 0.75;
		affectedByGravity = true;
		localGravityScale = 1;
		jumpPower = 0;

		facingRight = sideFacing;


	}

	@Override
	public void init(LevelState levelState) {
		state = levelState;
		map = state.getTileMap();
	}

	private void movementLogic() {
		if(!(falling || jumping)) {
			int modifier;
			int xCheck;
			Entity.Direction directionMoving;
			if(facingRight) {
				modifier = 1;
				directionMoving = Entity.Direction.RIGHT;
				xCheck = (int)(x + Tile.TILE_SIZE);
			}
			else {
				modifier = -1;
				directionMoving = Entity.Direction.LEFT;
				xCheck = (int)(x - 2);
			}

			if(checkMapCollision((int)(x + (speed * modifier)), (int)y, (int)x, (int)y)) {
				if(map.getTile((int)x, (int)y + 33).isSolid()
						&& map.getTile(xCheck, (int)y + 33).isSolid()) {
					moveX(directionMoving, speed);
				} else
					facingRight = !facingRight;
			} else
				facingRight = !facingRight;
		}
	}

	double prevX = x;
	boolean wasFacingRight = facingRight;
	int timesTurned;
	private boolean isStuck() {
		if(x == prevX) {
			if(wasFacingRight != facingRight) {
				timesTurned++;
				wasFacingRight = facingRight;
			} else {
				timesTurned = 0;
			}
		} else {
			prevX = x;
			timesTurned = 0;
			wasFacingRight = facingRight;
		}

		if(timesTurned > 2) {
			
			for(int i = 0; i < Tile.TILE_SIZE; i++) {
				if(map.getTile((int)x + i, (int)y + 33).isSolid()) {
					x = x + i;
					return false;
				} else if(map.getTile((int)x - i, (int)y + 33).isSolid()) {
					x = x - i;
					return false;
				}
			}
			return true;
		}
		else 
			return false;
	}

	@Override
	public void updateLogic() {
		if(((LevelState)gsm.getCurrentState()).getEntityManager().checkPlayerCollision(this)) {
			((LevelState)gsm.getCurrentState()).getEntityManager().getPlayer().hurt(this);
		}
		movementLogic();
		tex.update();
		if(isStuck())
			die();
	}

	@Override
	public Entity clone(boolean sideFacing) {
		Entity returnEntity = new Wolfman(gsm, (int)x, (int)y, sideFacing);
		return returnEntity;
	}
}
