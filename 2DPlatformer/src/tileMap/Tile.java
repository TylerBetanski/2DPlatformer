package tileMap;

import java.awt.Graphics2D;

import gfx.Texture;

public class Tile {
	private Texture texture;
	private boolean solid;
	public static final int TILE_SIZE = 32;
	
	public Tile(Texture tex, boolean solid) {
		texture = tex;
		this.solid = solid;
	}

	
	public void draw(Graphics2D g, int x, int y) {
		texture.draw(g, x, y, TILE_SIZE, TILE_SIZE);
	}
	
	public void update() {
		
	}
	
	public boolean isSolid() { return solid; }
	public Texture getTexture() { return texture; }

}
