package tileMap;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Tileset {
	private BufferedImage tileset;
	private final Tile[][] TILES;
	
	public Tileset(int tileSize) {
		TILES = new Tile[tileset.getWidth()/tileSize][tileset.getHeight()/tileSize];
		for(int x = 0; x < TILES.length; x++) {
			for(int y = 0; y < TILES[x].length; y++) {
				
				Color pixelColor;
				for(int pixelX = 0 + (x * tileSize); pixelX < tileSize * (x + 1); pixelX++) {
					for(int pixelY = 0 + (y * tileSize); pixelY < tileSize * (y + 1); pixelY++) {
						pixelColor = new Color(tileset.getRGB(pixelX, pixelY));
						if(pixelColor.getRed() > 0 && pixelColor.getBlue() == 0) { // If the pixel is Red
							//TILES[x][y] = new Tile(tileset.getSubimage(x * tileSize, y * tileSize, tileSize, tileSize), Tile.SOLID);
						} else {
							//TILES[x][y] = new Tile(tileset.getSubimage(x * tileSize, y * tileSize, tileSize, tileSize), Tile.AIR);
						}
					}
				}
			}
		}
	}
	
	
}
