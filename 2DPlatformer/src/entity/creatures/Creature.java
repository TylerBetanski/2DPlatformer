package entity.creatures;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import entity.Entity;
import gameState.GameStateManager;
import gfx.Camera;
import gfx.Texture;

public class Creature extends Entity {
	protected int health, maxHealth, speed;
	protected boolean dead, jumping, falling, attacking;
	protected int moveFactor;

	public Creature(GameStateManager gsm, Texture tex, double x, double y, int width, int height, Rectangle bounds, boolean solid, 
			int maxHealth, int speed) {
		super(gsm, tex, x, y, width, height, bounds, solid);
		this.health = maxHealth;
		this.speed = speed;
	}

	@Override
	public void update() {
		tex.update();
		if(health <= 0)
			dead = true;
	}

	@Override
	public void draw(Graphics2D g, Camera camera) {
		if(camera.inBounds((int)x, (int)y))
			tex.draw(g, (int)x - camera.getX(), (int)y - camera.getY());
	}

}
