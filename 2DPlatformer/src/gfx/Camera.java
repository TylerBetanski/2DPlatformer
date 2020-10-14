package gfx;

import entity.Entity;
import gameState.GameStateManager;
import gameState.Level1State;
import gameState.LevelState;
import main.GamePanel;
import tiles.Tile;
import tiles.TileMap;

public class Camera {
	private double x, y;
	private int targetX, targetY;
	private double speed;
	private Entity focusedEntity;
	
	private GameStateManager gsm;
	
	public Camera(GameStateManager gsm, int x, int y, double speed) {
		this.gsm = gsm;
		this.x = x;
		this.y = y;
		this.targetX = x;
		this.targetY = y;
		this.speed = speed;
	}
	
	public void update() {
		System.out.println("Camera: " + x + ", " + y);
		
		if(focusedEntity != null) {
			setTarget((int)((focusedEntity.getX() + focusedEntity.getWidth() / 2) - (GamePanel.WIDTH / 2)),
					(int)((focusedEntity.getY() + focusedEntity.getHeight() / 2) - (GamePanel.HEIGHT / 2)));
		}
		double scalar = 1.0;
		if(x > targetX) {
			scalar = -1.0;
			if(x + scalar * speed < targetX)
				x = targetX;
			else
				x = x + scalar * speed;
		} else {
			if(x + scalar * speed > targetX)
				x = targetX;
			else
				x = x + scalar * speed;
		}
		
		scalar = 1.0;
		if(y > targetY) {
			scalar = -1.0;
			if(y + scalar * speed < targetY)
				y = targetY;
			else
				y = (int)(y + scalar * speed);
		} else {
			if(y + scalar * speed > targetY)
				y = targetY;
			else
				y = (int)(y + scalar * speed);
		}
		
		
		if(x < 0) {
			x = 0;
		} else if (x + GamePanel.WIDTH > ((LevelState)gsm.getCurrentState()).getTileMap().getWidth()) {
			x = ((LevelState)gsm.getCurrentState()).getTileMap().getWidth() - GamePanel.WIDTH;
		}
		
		if(y < 0) {
			y = 0;
		} else if (y + GamePanel.HEIGHT > ((LevelState)gsm.getCurrentState()).getTileMap().getHeight()) {
			y = ((LevelState)gsm.getCurrentState()).getTileMap().getHeight() - GamePanel.HEIGHT;
		}
		
	}
	
	public boolean inBounds(int x, int y) {
		if(x >= this.x - Tile.TILE_SIZE && x <= this.x + GamePanel.WIDTH + Tile.TILE_SIZE
			&& y >= this.y - Tile.TILE_SIZE && y <= this.y + GamePanel.HEIGHT + Tile.TILE_SIZE)
				return true;
			else
				return false;
	}
	
	public void setTarget(int x, int y) {
		targetX = x;
		targetY = y;
	}
	
	public double getX() { return x; }
	public void setX(int x) { this.x = x; }
	public double getY() { return y; }
	public void setY(int y) { this.y = y; }
	public Entity getFocusedEntity() { return focusedEntity; }
	public void setFocusedEntity(Entity e) { focusedEntity = e; }


}
