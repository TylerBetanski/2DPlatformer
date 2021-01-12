package assets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import gameState.GameStateManager;
import gameState.LevelState;
import gfx.AnimatedTexture;
import gfx.Background;
import gfx.Flipbook;
import gfx.Spritesheet;
import gfx.Texture;
import tiles.TileMap;
import tiles.Tileset;
import utils.Utils;

public class Assets {

	
	// TILESETS
	public static final ArrayList<Tileset> TILESETS = new ArrayList<Tileset>();
	public static final Tileset MANOR_TILESET = addTileset(TILESETS, new Tileset("/Tilesets/manorTileset.png"));
	public static final Tileset RUINS_TILESET = addTileset(TILESETS, new Tileset("/Tilesets/ruinsTileset.png"));
	
	// BACKGROUNDS
	public static final Background MENU_BG = new Background("/Backgrounds/menuBG.png", 1);
	public static final Background FANCY_BG = new Background("/Backgrounds/fancyBG.png", 1);
	public static final Background GRADIENT_BG = new Background("/Backgrounds/gradientBG.png", 1);
	public static final Background PARROT_BG = new Background("/Backgrounds/parrotBG.png", 1);
	public static final Background WATER_BG = new Background("/Backgrounds/waterBG.png", 1);
	public static final Background BLANK_BG = new Background("/Backgrounds/blankBG.png", 1);
	public static final Background LEVEL_1_BG = new Background(new TileMap("/Maps/manorLevel.tilemap"));

	// TEXTURES

	// Player
	public static final Spritesheet PLAYER_SHEET = new Spritesheet("/Textures/player_spritesheet.png");
	public static final Texture PLAYER_IDLE = new Texture(PLAYER_SHEET.getTextureAtIndex(0));
	public static final AnimatedTexture PLAYER_WALK = new AnimatedTexture(new Flipbook(new Texture[] {PLAYER_SHEET.getTextureAtIndex(1),
			PLAYER_SHEET.getTextureAtIndex(2),
			PLAYER_SHEET.getTextureAtIndex(1),
			PLAYER_SHEET.getTextureAtIndex(0)}, 
			new int[] {15, 15, 15, 15}));
	public static final Texture PLAYER_JUMP = new Texture(PLAYER_SHEET.getTextureAtIndex(3));
	public static final Texture PLAYER_IDLE_ASCEND_STAIRS = new Texture(PLAYER_SHEET.getTextureAtIndex(4));
	public static final Texture PLAYER_IDLE_DESCEND_STAIRS = new Texture(PLAYER_SHEET.getTextureAtIndex(5));
	public static AnimatedTexture PLAYER_WALKING_ASCEND_STAIRS = new AnimatedTexture(new Flipbook(new Texture[] {PLAYER_SHEET.getTextureAtIndex(4),
			PLAYER_SHEET.getTextureAtIndex(1)}, 
			new int[] {15,15}));
	public static AnimatedTexture PLAYER_WALKING_DESCEND_STAIRS = new AnimatedTexture(new Flipbook(new Texture[] {PLAYER_SHEET.getTextureAtIndex(5),
			PLAYER_SHEET.getTextureAtIndex(1)}, 
			new int[] {15,15}));
	public static final AnimatedTexture PLAYER_DIE = new AnimatedTexture(new Flipbook(new Texture[] {PLAYER_SHEET.getTextureAtIndex(6),
			PLAYER_SHEET.getTextureAtIndex(7)}, 
			new int[] {15, 15}));
	public static final AnimatedTexture PLAYER_ATTACK = new AnimatedTexture(new Flipbook(new Texture[] {PLAYER_SHEET.getTextureAtIndex(8),
			PLAYER_SHEET.getTextureAtIndex(9),
			PLAYER_SHEET.getTextureAtIndex(10)}, 
			new int[] {5, 5, 6}));

	public static final AnimatedTexture PLAYER_JUMP_ATTACK = new AnimatedTexture(new Flipbook(new Texture[] {PLAYER_SHEET.getTextureAtIndex(11),
			PLAYER_SHEET.getTextureAtIndex(12),
			PLAYER_SHEET.getTextureAtIndex(13)}, 
			new int[] {5, 5, 6}));
	public static final AnimatedTexture PLAYER_DESCEND_STAIRS_ATTACK = new AnimatedTexture(new Flipbook(new Texture[] {PLAYER_SHEET.getTextureAtIndex(14),
			PLAYER_SHEET.getTextureAtIndex(15),
			PLAYER_SHEET.getTextureAtIndex(16)}, 
			new int[] {5, 5, 6}));
	public static final AnimatedTexture PLAYER_ASCEND_STAIRS_ATTACK = new AnimatedTexture(new Flipbook(new Texture[] {PLAYER_SHEET.getTextureAtIndex(17),
			PLAYER_SHEET.getTextureAtIndex(18),
			PLAYER_SHEET.getTextureAtIndex(19)}, 
			new int[] {5, 5, 6}));
	public static final Texture PLAYER_BLOCK_STANDING = new Texture(PLAYER_SHEET.getTextureAtIndex(20));
	public static final Texture PLAYER_BLOCK_CROUCHING = new Texture(PLAYER_SHEET.getTextureAtIndex(21));

	// Enemies
	public static final Spritesheet ENEMIES_SHEET = new Spritesheet("/Textures/enemies_spritesheet.png");
	public static final Texture GHOUL_NORMAL = new Texture(ENEMIES_SHEET.getTextureAtIndex(0));
	public static final Texture GHOUL_CHARGE = new Texture(ENEMIES_SHEET.getTextureAtIndex(1));
	public static final AnimatedTexture WOLFMAN = new AnimatedTexture(new Flipbook(new Texture[] {ENEMIES_SHEET.getTextureAtIndex(2),
			ENEMIES_SHEET.getTextureAtIndex(3)}, 
			new int[] {15, 15}));
	public static final AnimatedTexture TRIDENTMAN = new AnimatedTexture(new Flipbook(new Texture[] {ENEMIES_SHEET.getTextureAtIndex(4),
			ENEMIES_SHEET.getTextureAtIndex(5)}, 
			new int[] {15, 15}));
	public static final Texture HAND_OPEN = new Texture(ENEMIES_SHEET.getTextureAtIndex(6));
	public static final Texture HAND_CLOSED = new Texture(ENEMIES_SHEET.getTextureAtIndex(7));
	public static final AnimatedTexture BAT = new AnimatedTexture(new Flipbook(new Texture[] {ENEMIES_SHEET.getTextureAtIndex(8),
			ENEMIES_SHEET.getTextureAtIndex(9)}, 
			new int[] {15, 15}));
	public static final AnimatedTexture FISHMAN_WALK = new AnimatedTexture(new Flipbook(new Texture[] {ENEMIES_SHEET.getTextureAtIndex(11),
			ENEMIES_SHEET.getTextureAtIndex(12)}, 
			new int[] {15, 15}));
	public static final Texture FISHMAN_ATTACK = new Texture(ENEMIES_SHEET.getTextureAtIndex(13));
	public static final Texture FIREBALL = new Texture(ENEMIES_SHEET.getTextureAtIndex(14));
	public static final Texture PLANT = new Texture(ENEMIES_SHEET.getTextureAtIndex(15));
	public static final Texture PLANT_SHOOT = new Texture(ENEMIES_SHEET.getTextureAtIndex(16));

	// Soul
	public static final Texture SOUL = new Texture(loadImage("/Textures/soul.png"));
	
	// Save Statue
	public static final Texture SAVE_STATUE = new Texture(loadImage("/Textures/save_statue.png"));
	
	// Doge
	public static final Texture DOGE_TEXTURE = new Texture(loadImage("/Textures/doge.png"));
	public static final Texture CHEEMS_TEXTURE = new Texture(loadImage("/Textures/cheems.png"));
	public static final Texture DOGE_2_TEXTURE = new Texture(loadImage("/Textures/doge_2.png"));
	
	// Health Bars
	public static final Spritesheet HEALTH_SHEET = new Spritesheet("/Textures/health_spritesheet.png");
	
	
	// LEVELS
	public static final ArrayList<LevelState> LEVELS = new ArrayList<LevelState>();
	public static LevelState MANOR_LEVEL;
	public static LevelState RUINS_LEVEL;

	public static void init(GameStateManager gsm) {
		//Utils.convertImageToTilemap(loadImage("/Maps/ruinsLevel.png"));
		MANOR_LEVEL = new LevelState(gsm, "manorLevel", 592, 592, true);
		RUINS_LEVEL = new LevelState(gsm, "ruinsLevel", 128, 128, true);
		
		LEVELS.add(MANOR_LEVEL);
		LEVELS.add(RUINS_LEVEL);
	}

	public static Tileset addTileset(ArrayList<Tileset> arrayList, Tileset tileset) {
		arrayList.add(tileset);
		return tileset;
	}

	public static BufferedImage loadImage(String path) {

		try {
			return ImageIO.read(Assets.class.getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
