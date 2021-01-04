package tiles;

import java.awt.Graphics2D;

import gfx.Texture;

public class AirTile extends Tile {

	public AirTile() {
		super(null, false, false, false);
	}
	
	@Override
	public void draw(Graphics2D g, int x, int y) {}
	@Override
	public void update() {}
	@Override
	public Texture getTexture() { return null; }

}
