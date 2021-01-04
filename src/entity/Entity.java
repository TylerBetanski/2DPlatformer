package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import gameState.GameStateManager;
import gameState.LevelState;
import gfx.Camera;
import gfx.Texture;
import tiles.Tile;
import tiles.TileMap;

public abstract class Entity {

	public enum Direction {
		UP, 
		DOWN,
		LEFT,
		RIGHT
	}

	protected GameStateManager gsm;

	protected Texture tex;
	protected double x, y;
	protected Rectangle bounds;
	protected boolean solid;
	
	protected LevelState state;


	public Entity(GameStateManager gsm, Texture tex, double x, double y, boolean solid) {
		this.gsm = gsm;
		this.tex = tex;
		this.x = x;
		this.y = y;
		this.solid = solid;
		bounds = new Rectangle(0, 0, tex.getWidth(), tex.getHeight());
	}

	public Entity(GameStateManager gsm, Texture tex, double x, double y, Rectangle bounds) {
		this.gsm = gsm;
		this.tex = tex;
		this.x = x;
		this.y = y;
		this.bounds = bounds;
		this.solid = true;
	}
	
	public void init(LevelState levelState) {
		state = levelState;
	}

	public boolean intersects(Entity object) {
		Rectangle bounds1 = this.getBounds();
		Rectangle bounds2 = object.getBounds();
		return bounds1.intersects(bounds2);
	}

	public boolean collides(Entity object) {
		Rectangle bounds1 = this.getBounds();
		Rectangle bounds2 = object.getBounds();
		return (bounds1.intersects(bounds2) && (this.isSolid() && object.isSolid()));
	}

	public boolean checkMapCollision(int x, int y, int startX, int startY) {
		int xMin = (int)(x + bounds.getX()) / Tile.TILE_SIZE;
		int xMax = (int)((x + bounds.getX() + bounds.getWidth()) / Tile.TILE_SIZE);
		int yMin = (int)(y + bounds.getY()) / Tile.TILE_SIZE;
		int yMax = (int)((y + bounds.getY() + bounds.getHeight()) / Tile.TILE_SIZE);
		TileMap tileMap = ((LevelState)gsm.getCurrentState()).getTileMap();
		for(int x1 = xMin; x1 <= xMax; x1++) {
			for(int y1 = yMin; y1 <= yMax; y1++) {
				if(startY < y && tileMap.getTileAtIndex(x1, yMax).isPlatformTile()) {
					return false;
				}
				if(tileMap.getTileAtIndex(x1, y1).isSolid())
					return false;
			}
		}
		return true;
	}

	public abstract void update();
	public abstract void draw(Graphics2D g, Camera camera);

	public Texture getTexture() { return tex; }
	public double getX() { return x; }
	public double getY() { return y; }
	public void setX(int x) { this.x = x; } 
	public void setY(int y) { this.y = y; } 
	public int getWidth() { return (int)bounds.getWidth(); }
	public int getHeight() { return (int)bounds.getHeight(); }
	public Rectangle getBounds() { return bounds; }
	public boolean isSolid() { return solid; }
}
