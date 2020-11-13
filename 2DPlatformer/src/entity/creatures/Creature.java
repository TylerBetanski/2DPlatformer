package entity.creatures;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import entity.Entity;
import gameState.GameStateManager;
import gameState.LevelState;
import gfx.Camera;
import gfx.Texture;
import main.GamePanel;
import tiles.AirTile;
import tiles.Tile;

public abstract class Creature extends Entity {
	protected int health, maxHealth;
	protected double speed;
	protected double yVelocity;
	protected boolean dead, jumping, falling, attacking;
	protected boolean affectedByGravity;
	protected double localGravityScale;
	protected int jumpPower = 6;

	public Creature(GameStateManager gsm, Texture tex, double x, double y, Rectangle bounds, int maxHealth, double speed, double gravityScale) {
		super(gsm, tex, x, y, bounds);
		this.health = maxHealth;
		this.speed = speed;
		localGravityScale = gravityScale;
		if(localGravityScale != 0)
			affectedByGravity = true;
	}

	public Creature(GameStateManager gsm, Texture tex, double x, double y, int maxHealth, double speed, double gravityScale, boolean solid) {
		super(gsm, tex, x, y, solid);
		this.health = maxHealth;
		this.speed = speed;
		localGravityScale = gravityScale;
		if(localGravityScale != 0)
			affectedByGravity = true;
	}
	
	public Creature(GameStateManager gsm, Texture tex, double x, double y, Rectangle bounds, boolean solid) {
		super(gsm, tex, x, y, bounds);
	}

	protected void moveX(Entity.Direction direction, double moveSpeed) {
		if(direction == Entity.Direction.RIGHT) {
			if(checkMapCollision((int)(x + moveSpeed), (int)y)) {
				x += moveSpeed;
			} else {
				x = Math.ceil(((x + moveSpeed)) / Tile.TILE_SIZE) * Tile.TILE_SIZE - bounds.getX() - bounds.getWidth() - 1;
			}
		} else {
			if(checkMapCollision((int)(x - moveSpeed), (int)y)) {
				x -= moveSpeed;
			} else {
				x = Math.floor(((x - moveSpeed)) / Tile.TILE_SIZE) * Tile.TILE_SIZE + Tile.TILE_SIZE - bounds.getX();
			}
		}
	}

	protected void moveY(Entity.Direction direction, double moveSpeed) {
		if(direction == Entity.Direction.UP) {
			if(checkMapCollision((int)x, (int)(y - moveSpeed))) {
				y -= moveSpeed;
			} else {
				y = Math.ceil(((y - moveSpeed)) / Tile.TILE_SIZE) * Tile.TILE_SIZE - bounds.getY();
				yVelocity = 0;
			}
		} else {
			if(checkMapCollision((int)x, (int)(y + moveSpeed))) {
				falling = true;
				y += moveSpeed;
			} else {
				y = Math.floor(((y + moveSpeed)) / Tile.TILE_SIZE) * Tile.TILE_SIZE;
				jumping = false;
				falling = false;
			}
		}
	}
	
	protected void gravity() {
		yVelocity -= (((LevelState)gsm.getCurrentState()).getGravityScale() * localGravityScale) / 60.0;
		yVelocity = Math.max(-3, yVelocity);
		if(yVelocity < 0) {
			moveY(Entity.Direction.DOWN, -yVelocity);
		} else {
			moveY(Entity.Direction.UP, yVelocity);
		}
	}
	
	protected void jump() {
		if(!jumping && !falling) {
			jumping = true;
			yVelocity = jumpPower;
		}
	}
	
	protected Tile getTileStandingOn(Entity.Direction direction) {
		Tile standingTile;
		if(!affectedByGravity)
			standingTile = new AirTile();
		else {
			if(direction == Entity.Direction.LEFT) {
				standingTile = ((LevelState)gsm.getCurrentState()).getTileMap().getTile((int)(x + bounds.getX()),(int)(y + bounds.getY() + bounds.getHeight() + 1));
			} else {
				standingTile = ((LevelState)gsm.getCurrentState()).getTileMap().getTile((int)(x + bounds.getX() + bounds.getWidth()),(int)(y + bounds.getY() + bounds.getHeight() + 1));
			}
		}
		return standingTile;
	}

	@Override
	public abstract void update();

	protected void handleInput() {
		
	};

	@Override
	public void draw(Graphics2D g, Camera camera) {
		if(camera.inBounds((int)x, (int)y)) {
			tex.draw(g, (int)x - (int)camera.getX(), (int)y - (int)camera.getY());

			if(GamePanel.DEBUG_RENDERBOXES) {
				g.setColor(Color.red);
				g.drawRect((int)x + (int)bounds.getX() - (int)camera.getX(), (int)y + (int)bounds.getY()- (int)camera.getY(), (int)bounds.getWidth(), (int)bounds.getHeight());
			}
		}
	}
}
