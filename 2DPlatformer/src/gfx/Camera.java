package gfx;

import entity.Entity;
import main.GamePanel;
import tileMap.Tile;

public class Camera {
	private int x, y;
	private int targetX, targetY;
	private int speed;
	private Entity focusedEntity;
	
	public Camera(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		if(focusedEntity != null) {
			setTarget((int)((focusedEntity.getX() + focusedEntity.getWidth() / 2) - (GamePanel.WIDTH / 2)),
					(int)((focusedEntity.getY() + focusedEntity.getHeight() / 2) - (GamePanel.HEIGHT / 2)));
		}
		int scalar = 1;
		if(x > targetX) {
			scalar = -1;
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
		if(y > targetY) {
			scalar = -1;
			if(y + scalar * speed < targetY)
				y = targetY;
			else
				y = y + scalar * speed;
		} else {
			if(y + scalar * speed > targetY)
				y = targetX;
			else
				y = y + scalar * speed;
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
	
	public int getX() { return x; }
	public void setX(int x) { this.x = x; }
	public int getY() { return y; }
	public void setY(int y) { this.y = y; }
	public Entity getFocusedEntity() { return focusedEntity; }
	public void setFocusedEntity(Entity e) { focusedEntity = e; }


}
