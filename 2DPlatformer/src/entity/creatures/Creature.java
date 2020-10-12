package entity.creatures;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import entity.Entity;
import gameState.GameStateManager;
import gfx.Camera;
import gfx.Texture;

public class Creature extends Entity {
	protected int health, maxHealth, speed;
	protected boolean dead, jumping, falling, attacking;
	protected int moveFactorX, moveFactorY;

	public Creature(GameStateManager gsm, Texture tex, double x, double y, int width, int height, Rectangle bounds, boolean solid, 
			int maxHealth, int speed) {
		super(gsm, tex, x, y, width, height, bounds, solid);
		this.health = maxHealth;
		this.speed = speed;
		moveFactorX = 1;
		moveFactorY = 1;
	}

	public Creature(GameStateManager gsm, Texture tex, double x, double y, int width, int height, int maxHealth, int speed) {
		super(gsm, tex, x, y, width, height, new Rectangle(0,0, width, height), true);
		this.health = maxHealth;
		this.speed = speed;
		moveFactorX = 1;
		moveFactorY = 1;
	}

	@Override
	public void update() {
		// Update Position
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
