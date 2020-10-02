package assets;

import java.util.ArrayList;

import gfx.Background;
import tileMap.Tileset;

public class Assets {
	
	// Backgrounds
	public static final Background MENU_BG = new Background("/Backgrounds/menuBG.png", 1);
	public static final Background FANCY_BG = new Background("/Backgrounds/fancyBG.png", 1);
	public static final Background GRADIENT_BG = new Background("/Backgrounds/gradientBG.png", 1);
	public static final Background PARROT_BG = new Background("/Backgrounds/parrotBG.png", 1);
	public static final Background WATER_BG = new Background("/Backgrounds/waterBG.png", 1);
	
	// Tilesets
	public static final ArrayList<Tileset> TILESETS = new ArrayList<Tileset>();
	public static final Tileset GRASS_TILESET = addTileset(TILESETS, new Tileset("/Tilesets/grassTileset.png", false));

	public void init() {
		
	}
	
	public static Tileset addTileset(ArrayList<Tileset> arrayList, Tileset tileset) {
		arrayList.add(tileset);
		return tileset;
	}
}
