package tiles;

import java.awt.Graphics2D;

import assets.Assets;
import gfx.Camera;
import main.GamePanel;
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
		for(int x = (int)Math.floor(camera.getX() / Tile.TILE_SIZE); x < (int)Math.ceil((camera.getX() + GamePanel.WIDTH) / Tile.TILE_SIZE); x++) {
			for(int y = (int)Math.floor(camera.getY() / Tile.TILE_SIZE); y < (int)Math.ceil((camera.getY() + GamePanel.HEIGHT) / Tile.TILE_SIZE); y++) {
				tiles[x][y].draw(g, x * Tile.TILE_SIZE - (int)camera.getX(), y * Tile.TILE_SIZE - (int)camera.getY());
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

	public int getTileX(Tile tile) {
		for(int x = 0; x < tiles.length; x++) {
			for(int y = 0; y < tiles[x].length; y++) {
				if(tiles[x][y].equals(tile))
					return x;
			}
		}
		return 0;
	}

	public int getTileY(Tile tile) {
		for(int x = 0; x < tiles.length; x++) {
			for(int y = 0; y < tiles[x].length; y++) {
				if(tiles[x][y].equals(tile))
					return y;
			}
		}
		return 0;
	}

	public Tile getTileAtIndex(int x, int y) {
		if(x >= 0 && x < tiles.length && y >= 0 && y < tiles[0].length) {
			return tiles[x][y];
		} else
			return new AirTile();
	}

	public int getWidth() { return tiles.length * Tile.TILE_SIZE; }
	public int getHeight() { return tiles[0].length * Tile.TILE_SIZE; }
}
