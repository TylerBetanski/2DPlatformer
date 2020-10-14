package entity.creatures;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import entity.Entity;
import gameState.GameStateManager;
import gameState.LevelState;
import gfx.Camera;
import gfx.Texture;

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
		moveFactorX = 1;
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
			if(!checkMapCollision(direction))
				x += moveSpeed;
		} else {
			if(!checkMapCollision(direction))
				x -= moveSpeed;
		}
	}
	
	private void moveY(Entity.Direction direction, double moveSpeed) {
		if(direction == Entity.Direction.UP) {
			if(!checkMapCollision(direction))
				y += moveSpeed;
		} else {
			if(!checkMapCollision(direction))
				y -= moveSpeed;
		}
	}

	private void move() {
		if(moveFactorX != 0) {
			Entity.Direction xDirection = Entity.Direction.RIGHT;

			if(moveFactorX > 0)
				xDirection = Entity.Direction.RIGHT;
			else if(moveFactorX < 0)
				xDirection = Entity.Direction.RIGHT;

			if(!checkMapCollision(xDirection)) {
				x = x + moveFactorX * speed;
			}
		}
		
		if(moveFactorY != 0) {
			Entity.Direction yDirection = Entity.Direction.LEFT;


			if(moveFactorY > 0)
				yDirection = Entity.Direction.DOWN;
			else if(moveFactorY < 0)
				yDirection = Entity.Direction.UP;

			if(!checkMapCollision(yDirection)) {
				y = y + moveFactorY * speed;
			}
		}
	}

	private void gravity() {
		if(affectedByGravity) {
			if(!checkMapCollision(Entity.Direction.DOWN)) {
				if(!jumping)
					falling = true;
				y = y + ((LevelState)gsm.getCurrentState()).getGravityScale() * localGravityScale;
			}
			falling = false;
		} else
			falling = false;
	}
	
	public void jump() {
		if(!checkMapCollision(Entity.Direction.UP) && !falling) {
			jumping = true;
		} else
			jumping = false;
	}

	@Override
	public void update() {
		// Update Position
		moveX(Entity.Direction.RIGHT, speed);
		//gravity();

		tex.update();
		if(health <= 0)
			dead = true;
	}

	@Override
	public void draw(Graphics2D g, Camera camera) {
		if(camera.inBounds((int)x, (int)y)) {
			tex.draw(g, (int)x - (int)camera.getX(), (int)y - (int)camera.getY());

			g.setColor(Color.GREEN);
			System.out.println("X & Y: "+ x + ", " + y);
			System.out.println("W & H: "+ bounds.getWidth() + ", " + bounds.getHeight());
			g.drawRect((int)(x - camera.getX()), (int)(y - camera.getY()), (int)(bounds.getWidth()), (int)(bounds.getHeight()));
			g.setColor(Color.RED);
			g.drawRect((int)((x + moveFactorX * speed) - camera.getX()), (int)((y + moveFactorY * speed) - camera.getY()), (int)(bounds.getWidth()), (int)(bounds.getHeight()));
		}
	}
}
