package tileMap;

import java.awt.Color;
import java.awt.image.BufferedImage;

import gfx.Texture;
import utils.Utils;

public class Tileset {
	private Tile[] tiles;
	
	public Tileset(String loc) {
		BufferedImage tileset = Utils.loadImage(loc);
		tiles = new Tile[(int)((tileset.getWidth() / Tile.TILE_SIZE) * (tileset.getHeight() / Tile.TILE_SIZE))];
		
		for(int x = 0; x < tileset.getWidth() / Tile.TILE_SIZE; x++) {
			for(int y = 0; y < tileset.getHeight() / Tile.TILE_SIZE; y++) {
				boolean solidFlag = false;
				Color pixelColor;
				for(int pixelX = x * Tile.TILE_SIZE; pixelX < x * Tile.TILE_SIZE + Tile.TILE_SIZE; pixelX++) {
					for(int pixelY = y * Tile.TILE_SIZE; pixelY < y * Tile.TILE_SIZE + Tile.TILE_SIZE; pixelY++) {
						pixelColor = new Color(tileset.getRGB(pixelX, pixelY));
						if(pixelColor.getRed() > 0 && pixelColor.getBlue() == 0) {
							solidFlag = true;
							tileset.setRGB(pixelX, pixelY, Color.WHITE.getRGB());
						}
					}
				}
				if(solidFlag)
					tiles[x + x*y] = new Tile(new Texture(tileset.getSubimage(x * Tile.TILE_SIZE, y, Tile.TILE_SIZE, Tile.TILE_SIZE)), true);
				else
					tiles[x + x*y] = new Tile(new Texture(tileset.getSubimage(x * Tile.TILE_SIZE, y, Tile.TILE_SIZE, Tile.TILE_SIZE)), false);
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
