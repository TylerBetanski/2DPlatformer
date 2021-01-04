package entity.creatures.enemies;


import java.awt.Rectangle;

import com.sun.tools.sjavac.Log.Level;

import assets.Assets;
import entity.Entity;
import entity.creatures.Creature;
import gameState.GameStateManager;
import gameState.LevelState;
import gfx.Texture;
import tiles.Tile;
import tiles.TileMap;

public class Ghoul extends Creature {

	private static Texture normalTexture = new Texture(Assets.GHOUL_NORMAL);
	private static Texture chargingTexture = new Texture(Assets.GHOUL_CHARGE);

	private static Rectangle bounds = new Rectangle(0,0,15,31);

	private boolean charging, stunned;
	
	TileMap map;

	public Ghoul(GameStateManager gsm, int x, int y, boolean sideFacing) {
		super(gsm, normalTexture, x, y, bounds, true);

		maxHealth = 1;
		health = maxHealth;
		speed = 0.5;
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
					if(charging) {
						stopCharging();
					} else
						facingRight = !facingRight;
			} else
				if(charging) {
					stopCharging();
				} else
					facingRight = !facingRight;
		}
	}

	private void checkVision() {
		int playerX = (int)((LevelState)gsm.getCurrentState()).getEntityManager().getPlayer().getX();
		int playerY = (int)((LevelState)gsm.getCurrentState()).getEntityManager().getPlayer().getY();

		if(((playerX > x && facingRight) || (playerX < x && !facingRight)) && Math.abs(playerY - y) < 32) {
			TileMap map = ((LevelState)gsm.getCurrentState()).getTileMap();
			int startTile = (int)(x / Tile.TILE_SIZE);
			int endTile = (int)(playerX / Tile.TILE_SIZE);

			int yTile = (int)(y / Tile.TILE_SIZE);

			boolean obstructed = false;
			if(facingRight) {
				for(int i = startTile; i < endTile; i++) {
					if(map.getTileAtIndex(i, yTile).isSolid())
						obstructed = true;
				}
			} else {
				for(int i = endTile; i > startTile; i--) {
					if(map.getTileAtIndex(i, yTile).isSolid())
						obstructed = true;
				}
			}
			if(!obstructed && state.getCamera().inBounds((int)x, (int)y)) {
				speed = 2.5;
				charging = true;
			}
		}
	}
	
	private int stunFrames = 60;
	private void stun() {
		stunFrames--;
		if(stunFrames < 0) {
			stunFrames = 60;
			stunned = false;
			facingRight = !facingRight;
		}
	}

	private boolean stopCharging() {
		if(charging) {
			charging = false;
			speed = 0.5;
			stunned = true;
			tex = normalTexture;
			return true;
		} else
			return false;
	}

	@Override
	public void updateLogic() {
		if(stunned) {
			stun();
		} else {
			if(((LevelState)gsm.getCurrentState()).getEntityManager().checkPlayerCollision(this)) {
				((LevelState)gsm.getCurrentState()).getEntityManager().getPlayer().hurt(this);
			}

			if(!charging) {
				checkVision();
			} else {
				if(!tex.equals(chargingTexture))
					tex = chargingTexture;
			}
			movementLogic();
			gravity();
		}
	}
	
	@Override
	public Entity clone(boolean sideFacing) {
		Entity returnEntity = new Ghoul(gsm, (int)x, (int)y, sideFacing);
		return returnEntity;
	}
}
