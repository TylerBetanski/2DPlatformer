package tiles;

import java.awt.image.BufferedImage;

import assets.Assets;
import gfx.Texture;
import utils.Utils;

public class Tileset {
	private Tile[] tiles;

	public Tileset(String loc) {
		BufferedImage tileset = Assets.loadImage(loc);
		tiles = new Tile[(int)((tileset.getWidth() / Tile.TILE_SIZE) * (tileset.getHeight() / Tile.TILE_SIZE))];
		String tileData = Utils.loadFileAsString(loc.substring(0, loc.indexOf(".")) + ".tiledata");
		String[] tokens = tileData.split("\\s+");
		for(int y = 0; y < tileset.getHeight() / Tile.TILE_SIZE; y++) {
			for(int x = 0; x < tileset.getWidth() / Tile.TILE_SIZE; x++) {
				tiles[x + y*(tileset.getWidth() / Tile.TILE_SIZE)] = new Tile(new Texture(tileset.getSubimage(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE,Tile.TILE_SIZE, Tile.TILE_SIZE)), 
						Integer.parseInt(tokens[x + (tileset.getWidth() / Tile.TILE_SIZE)*y]) == 1,
						(Integer.parseInt(tokens[x + (tileset.getWidth() / Tile.TILE_SIZE)*y]) == 2 || Integer.parseInt(tokens[x + (tileset.getWidth() / Tile.TILE_SIZE)*y]) == 4),
						Integer.parseInt(tokens[x + (tileset.getWidth() / Tile.TILE_SIZE)*y]) == 3 || Integer.parseInt(tokens[x + (tileset.getWidth() / Tile.TILE_SIZE)*y]) == 4);
			}
		}

	}

	public Tile[] getTiles() {
		return tiles;
	}

	public Tile getTile(int index) {
		if(index < tiles.length)
			return tiles[index];
		else
			return null;
	}
}
