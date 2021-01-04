package tiles;

import java.awt.Graphics2D;

import gfx.Texture;

public class Tile {
	private Texture texture;
	private boolean solid;
	private boolean stairTile;
	private boolean platformTile;
	public static final int TILE_SIZE = 16;
	
	public Tile(Texture tex, boolean solid, boolean stairTile, boolean platformTile) {
		texture = tex;
		this.solid = solid;
		this.stairTile = stairTile;
		this.platformTile = platformTile;
	}

	
	public void draw(Graphics2D g, int x, int y) {
		texture.draw(g, x, y, TILE_SIZE, TILE_SIZE);
	}
	
	public void update() {
		texture.update();
	}
	
	public boolean isSolid() { return solid; }
	public boolean isStairTile() { return stairTile; }
	public boolean isPlatformTile() { return platformTile; }
	public Texture getTexture() { return texture; }

}
