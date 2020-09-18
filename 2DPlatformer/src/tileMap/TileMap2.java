package tileMap;

import utils.Utils;

public class TileMap2 {
	private Tile[][] tiles;
	
	public TileMap2(String loc) {
		loadMap(loc);
	}
	
	public void loadMap(String loc) {
		String map = Utils.loadFileAsString(loc);
		String[] tokens = map.split("\\s+");
		tiles = new Tile[Integer.parseInt(tokens[0])][Integer.parseInt(tokens[1])];
		for(int i = 2; i < tokens.length; i++)
			System.out.println(tokens[i]);
	}
}
