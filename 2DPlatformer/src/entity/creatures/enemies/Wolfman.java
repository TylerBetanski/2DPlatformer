package entity.creatures.enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import assets.Assets;
import entity.Entity;
import entity.creatures.Creature;
import gameState.GameStateManager;
import gameState.LevelState;
import gfx.AnimatedTexture;
import gfx.Camera;
import main.GamePanel;
import tiles.TileMap;

public class Wolfman extends Creature {
	
	private static Rectangle bounds = new Rectangle(0,0,15,31);
	
	private boolean facingRight = true;
	
	public Wolfman(GameStateManager gsm, int x, int y) {
		super(gsm, new AnimatedTexture(Assets.WOLFMAN), x, y, bounds, true);
		
		maxHealth = 2;
		health = maxHealth;
		speed = 1;
		affectedByGravity = true;
		localGravityScale = 1;
		jumpPower = 5;
	}
	
	private void movementLogic() {
		if(facingRight) {
			TileMap map = ((LevelState)gsm.getCurrentState()).getTileMap();
			int tileX = map.getTileX(getTileStandingOn(Entity.Direction.RIGHT));
			int tileY = map.getTileY(getTileStandingOn(Entity.Direction.RIGHT));
			if(map.getTileAtIndex(tileX + 1, tileY).isSolid()
					&& map.getTileAtIndex(tileX, tileY).isSolid()) {
				moveX(Entity.Direction.RIGHT, speed);
			} else {
				facingRight = false;
			}
			
			
		} else {
			TileMap map = ((LevelState)gsm.getCurrentState()).getTileMap();
			int tileX = map.getTileX(getTileStandingOn(Entity.Direction.LEFT));
			int tileY = map.getTileY(getTileStandingOn(Entity.Direction.LEFT));
			if(map.getTileAtIndex(tileX - 1, tileY).isSolid()
					&& map.getTileAtIndex(tileX, tileY).isSolid()) {
				if(!checkMapCollision((int)(x - speed), (int)y)) {
					facingRight = true;
				} else {
					moveX(Entity.Direction.LEFT, speed);
				}
				
			} else {
				facingRight = true;
			}
		}
	}

	@Override
	public void update() {
		movementLogic();
		tex.update();
		gravity();
	}
	
	@Override
	public void draw(Graphics2D g, Camera camera) {
		if(camera.inBounds((int)x, (int)y)) {
			if(facingRight)
				tex.draw(g, (int)x + 16 - (int)camera.getX(), (int)y - (int)camera.getY(), - tex.getWidth(), tex.getHeight());
			else
				tex.draw(g, (int)x - (int)camera.getX(), (int)y - (int)camera.getY());
			
			if(GamePanel.DEBUG_RENDERBOXES) {
				g.setColor(Color.red);
				g.drawRect((int)x + (int)bounds.getX() - (int)camera.getX(), (int)y + (int)bounds.getY()- (int)camera.getY(), (int)bounds.getWidth(), (int)bounds.getHeight());
			}
		}
	}
	
}
