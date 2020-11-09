package assets;

import java.util.ArrayList;

import gfx.AnimatedTexture;
import gfx.Background;
import gfx.Flipbook;
import gfx.Spritesheet;
import gfx.Texture;
import tiles.Tileset;

public class Assets {

	// BACKGROUNDS
	public static final Background MENU_BG = new Background("/Backgrounds/menuBG.png", 1);
	public static final Background FANCY_BG = new Background("/Backgrounds/fancyBG.png", 1);
	public static final Background GRADIENT_BG = new Background("/Backgrounds/gradientBG.png", 1);
	public static final Background PARROT_BG = new Background("/Backgrounds/parrotBG.png", 1);
	public static final Background WATER_BG = new Background("/Backgrounds/waterBG.png", 1);
	public static final Background BLANK_BG = new Background("/Backgrounds/blankBG.png", 1);

	// TILESETS
	public static final ArrayList<Tileset> TILESETS = new ArrayList<Tileset>();
	public static final Tileset GRASS_TILESET = addTileset(TILESETS, new Tileset("/Tilesets/grassTileset.png", false));
	public static final Tileset FLOOR_TILESET = addTileset(TILESETS, new Tileset("/Tilesets/floorsTileset.png", false));

	// TEXTURES

	// Player
	public static final Spritesheet PLAYER_SHEET = new Spritesheet("/Textures/player_spritesheet.png");
	public static final Texture PLAYER_IDLE = new Texture(PLAYER_SHEET.getTextureAtIndex(0));
	public static final AnimatedTexture PLAYER_WALK = new AnimatedTexture(new Flipbook(new Texture[] {PLAYER_SHEET.getTextureAtIndex(1),
			PLAYER_SHEET.getTextureAtIndex(2),
			PLAYER_SHEET.getTextureAtIndex(1),
			PLAYER_SHEET.getTextureAtIndex(0)}, 
			new int[] {90, 90, 90, 90}));
	public static final Texture PLAYER_JUMP = new Texture(PLAYER_SHEET.getTextureAtIndex(3));
	public static final AnimatedTexture PLAYER_DIE = new AnimatedTexture(new Flipbook(new Texture[] {PLAYER_SHEET.getTextureAtIndex(6),
			PLAYER_SHEET.getTextureAtIndex(7)}, 
			new int[] {15, 15}));
	public static final AnimatedTexture PLAYER_ATTACK = new AnimatedTexture(new Flipbook(new Texture[] {PLAYER_SHEET.getTextureAtIndex(8),
			PLAYER_SHEET.getTextureAtIndex(9),
			PLAYER_SHEET.getTextureAtIndex(10)}, 
			new int[] {60, 60, 60}));
	
	public static final AnimatedTexture PLAYER_JUMP_ATTACK = new AnimatedTexture(new Flipbook(new Texture[] {PLAYER_SHEET.getTextureAtIndex(11),
			PLAYER_SHEET.getTextureAtIndex(12),
			PLAYER_SHEET.getTextureAtIndex(13)}, 
			new int[] {10, 10, 15}));

	public static void init() {
		//Utils.convertImageToTilemap(Utils.loadImage("/Maps/testLevel2.png"));
	}

	public static Tileset addTileset(ArrayList<Tileset> arrayList, Tileset tileset) {
		arrayList.add(tileset);
		return tileset;
	}
}
