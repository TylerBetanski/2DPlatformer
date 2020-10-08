package tileMap;

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
				if(tiles[x][y] != null)
					if(camera.inBounds(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE))
						tiles[x][y].draw(g, x * Tile.TILE_SIZE - camera.getX(), y * Tile.TILE_SIZE - camera.getY());
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
		return tiles[(int)(x / Tile.TILE_SIZE)][(int)(y / Tile.TILE_SIZE)];
	}
}
