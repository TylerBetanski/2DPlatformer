package entity.creatures.enemies;


import java.awt.Rectangle;

import assets.Assets;
import entity.Entity;
import entity.creatures.Creature;
import gameState.GameStateManager;
import gameState.LevelState;
import gfx.AnimatedTexture;
import gfx.Texture;
import tiles.Tile;
import tiles.TileMap;

public class Fishman extends Creature {

	private static AnimatedTexture walkingTexture = new AnimatedTexture(Assets.FISHMAN_WALK);
	private static Texture attackTexture = new Texture(Assets.FISHMAN_ATTACK);
	
	private static Rectangle bounds = new Rectangle(0,0,15,31);
	TileMap map;
	
	int maxShootCooldown = 180;
	int shootCooldown = 0;

	public Fishman(GameStateManager gsm, int x, int y, boolean sideFacing) {
		super(gsm, walkingTexture, x, y, bounds, true);

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
	
	private void shoot() {
		if(shootCooldown == 0) {
			state.getEntityManager().addEntity(new Fireball(gsm, (int)x, (int)y, false));
			shootCooldown = maxShootCooldown;
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
				shoot();
			}
		}
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
		checkVision();
		
		if(shootCooldown > 0)
			shootCooldown--;
		
		tex.update();
		if(isStuck())
			die();
	}

	@Override
	public Entity clone(boolean sideFacing) {
		Entity returnEntity = new Fishman(gsm, (int)x, (int)y, sideFacing);
		return returnEntity;
	}
}
