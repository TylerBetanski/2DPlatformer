package tileMap;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import utils.Utils;

public class TileMap {
	// Position
	private double x, y;
	private Rectangle bounds;
	
	private double tween;
	
	// Map
	private int[][] map;
	private int tileSize;
	private int numRows;
	private int numCols;
	private int width;
	private int height;
	
	// Tileset
	private BufferedImage tileset;
	private int numTilesAcross;
	private Tile[][] tiles;
	
	// Drawing Bounds
	private int rowOffset;
	private int colOffset;
	private int numRowsToDraw;
	private int numColsToDraw;
	
	public TileMap(int tilesize) {
		this.tileSize = tilesize;
		numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
		numColsToDraw = GamePanel.WIDTH / tileSize + 2;
		tween = 0.07;
	}
	
	public void loadTiles(String s) {
		tileset = Utils.loadImage("/Tilesets/testTileset,png");
		numTilesAcross = tileset.getWidth() / tileSize;
		tiles = new Tile[1][numTilesAcross];
		//tiles[0][0] = new Tile(, Tile.NORMAL);
	}
	
	public void loadMap() {
		
	}
}
