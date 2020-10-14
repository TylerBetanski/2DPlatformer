package tiles;

import java.awt.Color;
import java.awt.Graphics2D;

import assets.Assets;
import gfx.Camera;
import utils.Utils;

public class TileMap {
	private Tile[][] tiles;

	public TileMap(String loc) {
		loadMap(loc);
	}

	public void loadMap(String loc) {
		String map = Utils.loadFileAsString(loc);
		String[] tokens = map.split("\\s+");
		tiles = new Tile[Integer.parseInt(tokens[0])][Integer.parseInt(tokens[1])];
		for(int y = 0; y < tiles[0].length; y++) {
			for(int x = 0; x < tiles.length; x++) {
				String[] tileToken = tokens[(x + y * tiles.length) + 2].split("#");
				if(tileToken[0].equals("000")) {
					tiles[x][y] = new AirTile();
				} else {
					tiles[x][y] = Assets.TILESETS.get(Integer.parseInt(tileToken[0])).getTile(Integer.parseInt(tileToken[1]));
				}
			}
		}
	}

	public void draw(Graphics2D g, Camera camera) {
		for(int x = 0; x < tiles.length; x++) {
			for(int y = 0; y < tiles[x].length; y++) {
				if(tiles[x][y] != null) {
					if(camera.inBounds(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE)) {
						tiles[x][y].draw(g, x * Tile.TILE_SIZE - (int)camera.getX(), y * Tile.TILE_SIZE - (int)camera.getY());
						g.setColor(Color.RED);
						if(tiles[x][y].isSolid())
							g.drawRect(x * Tile.TILE_SIZE - (int)camera.getX(), y * Tile.TILE_SIZE - (int)camera.getY(), Tile.TILE_SIZE, Tile.TILE_SIZE);
					}
				}
			}
		}
	}

	public void update() {
		for(Tile[] tileArray: tiles) {
			for(Tile tile: tileArray) {
				tile.update();
			}
		}
	}

	public Tile getTile(int x, int y) {
		if(x / Tile.TILE_SIZE >= 0 && y / Tile.TILE_SIZE >= 0 && x / Tile.TILE_SIZE < tiles.length && y / Tile.TILE_SIZE < tiles[0].length) {
			if(tiles[(int)(x / Tile.TILE_SIZE)][(int)(y / Tile.TILE_SIZE)] != null)
				return tiles[(int)(x / Tile.TILE_SIZE)][(int)(y / Tile.TILE_SIZE)];
			else
				return new AirTile();
		}
		return new AirTile();
	}
	
	public int getWidth() { return tiles.length * Tile.TILE_SIZE; }
	public int getHeight() { return tiles[0].length * Tile.TILE_SIZE; }
}
