package entity.creatures;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import entity.Entity;
import gameState.GameStateManager;
import gameState.LevelState;
import gfx.Camera;
import gfx.Texture;
import input.Keys;
import tiles.Tile;

public class Creature extends Entity {
	protected int health, maxHealth;
	public double speed;
	protected boolean dead, jumping, falling, attacking;
	protected int moveFactorX, moveFactorY;
	protected boolean affectedByGravity;
	protected double localGravityScale;
	protected int jumpPower = 5;

	public Creature(GameStateManager gsm, Texture tex, double x, double y, Rectangle bounds, int maxHealth, double speed, double gravityScale) {
		super(gsm, tex, x, y, bounds);
		this.health = maxHealth;
		this.speed = speed;
		localGravityScale = gravityScale;
		if(localGravityScale != 0)
			affectedByGravity = true;
		moveFactorX = 0;
		moveFactorY = 0;
	}

	public Creature(GameStateManager gsm, Texture tex, double x, double y, int maxHealth, double speed, double gravityScale, boolean solid) {
		super(gsm, tex, x, y, solid);
		this.health = maxHealth;
		this.speed = speed;
		localGravityScale = gravityScale;
		if(localGravityScale != 0)
			affectedByGravity = true;
		moveFactorX = 1;
		moveFactorY = 0;
	}

	private void moveX(Entity.Direction direction, double moveSpeed) {
		if(direction == Entity.Direction.RIGHT) {
			if(checkMapCollisionSimple((int)(x + moveSpeed), (int)y)) {
				x += moveSpeed;
			} else {
				x = Math.ceil(((x + moveSpeed)) / Tile.TILE_SIZE) * Tile.TILE_SIZE - bounds.getX() - bounds.getWidth();
			}
		} else {
			if(checkMapCollisionSimple((int)(x - moveSpeed), (int)y)) {
				x -= moveSpeed;
			} else {
				x = Math.floor(((x - moveSpeed)) / Tile.TILE_SIZE) * Tile.TILE_SIZE + Tile.TILE_SIZE - bounds.getX();
			}
		}
	}

	private void moveY(Entity.Direction direction, double moveSpeed) {
		if(direction == Entity.Direction.UP) {
			if(checkMapCollisionSimple((int)x, (int)(y - moveSpeed))) {
				y -= moveSpeed;
			} else {
				y = Math.ceil(((y - moveSpeed)) / Tile.TILE_SIZE) * Tile.TILE_SIZE - bounds.getY();
			}
		} else {
			if(checkMapCollisionSimple((int)x, (int)(y + moveSpeed))) {
				y += moveSpeed;
			} else {
				y = Math.floor(((y + moveSpeed)) / Tile.TILE_SIZE) * Tile.TILE_SIZE + bounds.getY() - 1;
			}
		}
	}

	public void gravity() {
			if(!jumping) {
				if(!checkMapCollision(Entity.Direction.DOWN)) {
					falling = true;
					moveY(Entity.Direction.DOWN, ((LevelState)gsm.getCurrentState()).getGravityScale() * localGravityScale);
				} else
					falling = false;
			}
	}

	public void jump(int jumpPow) {
		if(!checkMapCollision(Entity.Direction.UP) && !falling) {
			if(jumpPow > 0) {
				y -= jumpPow + ((LevelState)gsm.getCurrentState()).getGravityScale() * localGravityScale;
				jump(jumpPow - 1);
			} else
				jumping = false;
		} else {
			jumping = false;
			falling = true;
		}
	}

	public void jump2() {
		if(!jumping && !falling) {
			jumping = true;
			y -= 5;
		}
	}


	@Override
	public void update() {
		handleInput();

		// Update Position
		if(affectedByGravity)
			gravity();
		tex.update();
		if(health <= 0)
			dead = true;
	}

	public void handleInput() {
		if(Keys.isHeld(Keys.LEFT)) {
			moveX(Entity.Direction.LEFT, 1);
		}
		if(Keys.isHeld(Keys.RIGHT)) {
			moveX(Entity.Direction.RIGHT, 1);
		}
		if(Keys.isPressed(Keys.UP)) {
			jump(5);
		}

	}

	@Override
	public void draw(Graphics2D g, Camera camera) {
		if(camera.inBounds((int)x, (int)y)) {
			tex.draw(g, (int)x - (int)camera.getX(), (int)y - (int)camera.getY());

			g.setColor(Color.red);
			g.drawRect((int)x + (int)bounds.getX() - (int)camera.getX(), (int)y + (int)bounds.getY()- (int)camera.getY(), (int)bounds.getWidth(), (int)bounds.getHeight());
			//System.out.println("X & Y: "+ x + ", " + y);
			//System.out.println("W & H: "+ bounds.getWidth() + ", " + bounds.getHeight());
		}
	}
}
