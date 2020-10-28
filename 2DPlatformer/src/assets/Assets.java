package assets;

import java.util.ArrayList;

import gfx.AnimatedTexture;
import gfx.Background;
import gfx.Flipbook;
import gfx.Spritesheet;
import tiles.Tileset;
import utils.Utils;

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
	public static final Tileset FLOOR_TILESET = addTileset(TILESETS, new Tileset("/Tilesets/floorsTileset.png", false));
	
	// Textures
	public static final Spritesheet DEMON_SHEET = new Spritesheet("/Textures/demon.png", 16, 16);
	public static final Flipbook DEMON_FLIPBOOK = new Flipbook(DEMON_SHEET);
	public static final AnimatedTexture DEMON = new AnimatedTexture(DEMON_FLIPBOOK);
	
	public static final Spritesheet PLAYER_SHEET = new Spritesheet("/Textures/player_spritesheet.png");
	public static final Flipbook PLAYER_FLIPBOOK = new Flipbook(PLAYER_SHEET);
	public static final AnimatedTexture PLAYER = new AnimatedTexture(PLAYER_FLIPBOOK);

	public static void init() {
		//Utils.convertImageToTilemap(Utils.loadImage("/Maps/testLevel2.png"));
	}
	
	public static Tileset addTileset(ArrayList<Tileset> arrayList, Tileset tileset) {
		arrayList.add(tileset);
		return tileset;
	}
}
