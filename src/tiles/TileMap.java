package tiles;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

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
					if(GamePanel.DOGE_MODE) {
						Tile tile = Assets.TILESETS.get(Integer.parseInt(tileToken[0])).getTile(Integer.parseInt(tileToken[1]));
						if(tile.isSolid()) {
							tiles[x][y] = new Tile(Assets.DOGE_TEXTURE, true, false, false);
						} else if(tile.isStairTile() && !tile.isPlatformTile()) {
							tiles[x][y] = new Tile(Assets.DOGE_2_TEXTURE, false, true, false);
						} else if(tile.isStairTile() && tile.isPlatformTile()) {
							tiles[x][y] = new Tile(Assets.DOGE_2_TEXTURE, false, true, true);
						} else if(!tile.isStairTile() && tile.isPlatformTile()) {
							tiles[x][y] = new Tile(Assets.DOGE_2_TEXTURE, false, false, true);
						} else {
							tiles[x][y] = new Tile(Assets.CHEEMS_TEXTURE, false, false, false);
						}
					} else
						tiles[x][y] = Assets.TILESETS.get(Integer.parseInt(tileToken[0])).getTile(Integer.parseInt(tileToken[1]));
				}
			}
		}
	}

	public void draw(Graphics2D g, Camera camera) {
		for(int x = (int)Math.floor(camera.getX() / Tile.TILE_SIZE); x < (int)Math.ceil((camera.getX() + GamePanel.WIDTH) / Tile.TILE_SIZE); x++) {
			for(int y = (int)Math.floor(camera.getY() / Tile.TILE_SIZE); y < (int)Math.ceil((camera.getY() + GamePanel.HEIGHT) / Tile.TILE_SIZE); y++) {
				tiles[x][y].draw(g, x * Tile.TILE_SIZE - (int)camera.getX(), y * Tile.TILE_SIZE - (int)camera.getY());
				if(GamePanel.DEBUG_RENDERBOXES) {
					if(tiles[x][y].isSolid()) {
						g.setColor(Color.GREEN);
						if(y > 0 && !tiles[x][y-1].isSolid())
							g.drawLine((int)(x * Tile.TILE_SIZE - camera.getX()), (int)(y * Tile.TILE_SIZE - camera.getY()), 
									(int)(x * Tile.TILE_SIZE + 15 - camera.getX()), (int)(y * Tile.TILE_SIZE - camera.getY()));
						if(y < tiles[x].length - 1 && !tiles[x][y+1].isSolid())
							g.drawLine((int)(x * Tile.TILE_SIZE - camera.getX()), (int)(y * Tile.TILE_SIZE + 15 - camera.getY()), 
									(int)(x * Tile.TILE_SIZE + 15 - camera.getX()), (int)(y * Tile.TILE_SIZE + 15 - camera.getY()));
						if(x > 0 && !tiles[x-1][y].isSolid())
							g.drawLine((int)(x * Tile.TILE_SIZE - camera.getX()), (int)(y * Tile.TILE_SIZE - camera.getY()), 
									(int)(x * Tile.TILE_SIZE - camera.getX()), (int)(y * Tile.TILE_SIZE + 15 - camera.getY()));
						if(x < tiles.length - 1 && !tiles[x+1][y].isSolid())
							g.drawLine((int)(x * Tile.TILE_SIZE + 15 - camera.getX()), (int)(y * Tile.TILE_SIZE - camera.getY()), 
									(int)(x * Tile.TILE_SIZE + 15 - camera.getX()), (int)(y * Tile.TILE_SIZE + 15 - camera.getY()));
					} else if(tiles[x][y].isStairTile()) {
						g.setColor(Color.MAGENTA);
						if(y > 0 && !tiles[x][y-1].isStairTile())
							g.drawLine((int)(x * Tile.TILE_SIZE - camera.getX()), (int)(y * Tile.TILE_SIZE - camera.getY()), 
									(int)(x * Tile.TILE_SIZE + 15 - camera.getX()), (int)(y * Tile.TILE_SIZE - camera.getY()));
						if(y < tiles[x].length - 1 && !tiles[x][y+1].isStairTile())
							g.drawLine((int)(x * Tile.TILE_SIZE - camera.getX()), (int)(y * Tile.TILE_SIZE + 15 - camera.getY()), 
									(int)(x * Tile.TILE_SIZE + 15 - camera.getX()), (int)(y * Tile.TILE_SIZE + 15 - camera.getY()));
						if(x > 0 && !tiles[x-1][y].isStairTile())
							g.drawLine((int)(x * Tile.TILE_SIZE - camera.getX()), (int)(y * Tile.TILE_SIZE - camera.getY()), 
									(int)(x * Tile.TILE_SIZE - camera.getX()), (int)(y * Tile.TILE_SIZE + 15 - camera.getY()));
						if(x < tiles.length - 1 && !tiles[x+1][y].isStairTile())
							g.drawLine((int)(x * Tile.TILE_SIZE + 15 - camera.getX()), (int)(y * Tile.TILE_SIZE - camera.getY()), 
									(int)(x * Tile.TILE_SIZE + 15 - camera.getX()), (int)(y * Tile.TILE_SIZE + 15 - camera.getY()));
					} else if(tiles[x][y].isPlatformTile()) {
						g.setColor(Color.BLUE);
						if(y > 0 && (!tiles[x][y-1].isPlatformTile() || tiles[x][y-1].isStairTile()))
							g.drawLine((int)(x * Tile.TILE_SIZE - camera.getX()), (int)(y * Tile.TILE_SIZE - camera.getY()), 
									(int)(x * Tile.TILE_SIZE + 15 - camera.getX()), (int)(y * Tile.TILE_SIZE - camera.getY()));
						if(y < tiles[x].length - 1 && (!tiles[x][y+1].isPlatformTile() || tiles[x][y+1].isStairTile()))
							g.drawLine((int)(x * Tile.TILE_SIZE - camera.getX()), (int)(y * Tile.TILE_SIZE + 15 - camera.getY()), 
									(int)(x * Tile.TILE_SIZE + 15 - camera.getX()), (int)(y * Tile.TILE_SIZE + 15 - camera.getY()));
						if(x > 0 && (!tiles[x-1][y].isPlatformTile() || tiles[x-1][y].isStairTile()))
							g.drawLine((int)(x * Tile.TILE_SIZE - camera.getX()), (int)(y * Tile.TILE_SIZE - camera.getY()), 
									(int)(x * Tile.TILE_SIZE - camera.getX()), (int)(y * Tile.TILE_SIZE + 15 - camera.getY()));
						if(x < tiles.length - 1 && (!tiles[x+1][y].isPlatformTile() || tiles[x+1][y].isStairTile()))
							g.drawLine((int)(x * Tile.TILE_SIZE + 15 - camera.getX()), (int)(y * Tile.TILE_SIZE - camera.getY()), 
									(int)(x * Tile.TILE_SIZE + 15 - camera.getX()), (int)(y * Tile.TILE_SIZE + 15 - camera.getY()));
					}
				}
			}
		}
	}

	public void drawDebugTiles(Graphics2D g, Camera camera, int x, int y) {
		if(x < 1)
			x = 1;
		if(y < 1)
			y = 1;

		if(x < tiles.length - 2)
			x = tiles.length - 2;
		if(y > tiles[0].length - 2)
			y = tiles[0].length - 2;
		Tile aboveTile = tiles[x][y - 1];
		Tile belowTile = tiles[x][y + 1];
		Tile leftTile = tiles[x - 1][y ];
		Tile rightTile = tiles[x + 1][y ];
		System.out.println("X: "+x+", Y: "+y);
		g.setColor(Color.MAGENTA);
		if(tiles[x][y].isStairTile()) {
			g.drawRect(x * Tile.TILE_SIZE - (int)camera.getX(), y * Tile.TILE_SIZE - (int)camera.getY(), Tile.TILE_SIZE, Tile.TILE_SIZE);
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

	public BufferedImage toImage() {
		BufferedImage image = new BufferedImage(tiles.length * Tile.TILE_SIZE, tiles[0].length * Tile.TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
		for(int x = 0; x < tiles.length; x++) {
			for(int y = 0; y < tiles[0].length; y++) {
				BufferedImage tileImage = tiles[x][y].getTexture().getImage();


				for(int pixelX = 0; pixelX < Tile.TILE_SIZE; pixelX++) {
					for(int pixelY = 0; pixelY < Tile.TILE_SIZE; pixelY++) {
						int pixelColor = new Color(tileImage.getRGB(pixelX, pixelY)).getRGB();
						image.setRGB(pixelX + (x * Tile.TILE_SIZE), pixelY + (y * Tile.TILE_SIZE), pixelColor);
					}
				}
			}
		}

		return image;
	}

	public int getWidth() { return tiles.length * Tile.TILE_SIZE; }
	public int getHeight() { return tiles[0].length * Tile.TILE_SIZE; }
}
