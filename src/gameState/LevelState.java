package gameState;

import java.awt.Graphics2D;

import assets.Assets;
import entity.EntityManager;
import entity.creatures.enemies.*;
import entity.creatures.player.Player;
import gfx.Background;
import gfx.Camera;
import tiles.Tile;
import tiles.TileMap;
import utils.Utils;

public class LevelState extends GameState{

	protected String levelName;
	protected TileMap tileMap;
	protected Background bg;
	protected Camera camera;
	protected EntityManager entityManager;
	protected double gravityScale;
	protected String entityDataPath;
	protected String savesPath;
	protected int enterLeft;
	protected int enterRight;
	protected boolean enteredLeft;
	protected int index;

	protected Player player;
	protected int score;


	public LevelState(GameStateManager gameStateManager, String levelName, int enterLeft, int enterRight, boolean enteredLeft) {
		super(gameStateManager);
		this.levelName = levelName;
		this.enterLeft = enterLeft;
		this.enterRight = enterRight;
		this.enteredLeft = enteredLeft;
		init();
	}

	@Override
	public void init() {
		String path = "/Maps/" + levelName;
		tileMap = new TileMap((path + ".tilemap"));
		entityDataPath = (path + ".entity");
		entityManager = new EntityManager(gameStateManager);
		savesPath = (path + ".saves");
		loadEntities();		
		entityManager.init(this);
		player = entityManager.getPlayer();
		if(enteredLeft) {
			player.setX(0);
			player.setY(enterLeft);
		} else {
			player.setX(tileMap.getWidth() - Tile.TILE_SIZE);
			player.setY(enterRight);
		}
		bg = new Background(Assets.BLANK_BG);
		camera = new Camera(gameStateManager, 0, 0, 1);
		camera.setFocusedEntity(player);
		gravityScale = 22.5;
	}

	/* 0 - Player
	 * 1 - Wolfman
	 * 2 - Ghoul
	 */
	public void loadEntities() {
		String[] entityData = Utils.loadFileAsString(entityDataPath).split("\\R");

		for(int i = 0; i < entityData.length; i++) {
			String[] data = entityData[i].split(" / ");
			int index = Integer.parseInt(data[0]);
			int x = Integer.parseInt(data[1].split(",")[0]);
			int y = Integer.parseInt(data[1].split(",")[1]);
			boolean sideFacing = Boolean.parseBoolean(data[2]);
			if(index == 0) {
				entityManager.addEntity(new Player(gameStateManager, x, y, sideFacing));
			} else if(index == 1) {
				entityManager.addEntity(new Wolfman(gameStateManager, x, y, sideFacing));
			} else if(index == 2) {
				entityManager.addEntity(new Ghoul(gameStateManager, x, y, sideFacing));
			}
		}
	}

	public void moveToNextLevel(boolean fromRight) {
		if(index > 1 && !fromRight) {
			gameStateManager.setState(index - 1);
			((LevelState)gameStateManager.getCurrentState()).setEnteredLeft(false);
			((LevelState)gameStateManager.getCurrentState()).reload();
			gameStateManager.getSaveData().setCurrentLevelIndex(index - 1);
			gameStateManager.getSaveData().writeSaveData();
		} else if(index < Assets.LEVELS.size() + 1 && fromRight) {
			gameStateManager.setState(index + 1);
			((LevelState)gameStateManager.getCurrentState()).setEnteredLeft(true);
			((LevelState)gameStateManager.getCurrentState()).reload();
			gameStateManager.getSaveData().setCurrentLevelIndex(index + 1);
			gameStateManager.getSaveData().writeSaveData();
		}
	}

	public TileMap getTileMap() { return tileMap; }
	public Background getBackground() { return bg; }
	public Camera getCamera() { return camera; }
	public EntityManager getEntityManager() { return entityManager; }
	public double getGravityScale() { return gravityScale; }
	public void setBackground(Background bg) { this.bg = bg; }
	public void setEnteredLeft(boolean enteredLeft) { this.enteredLeft = enteredLeft; }
	public void setIndex(int i) { index = i; }

	@Override
	public void reload() {
		init();
		int respawnX = 0;
		int respawnY = enterLeft;
		if(!enteredLeft) {
			respawnX = tileMap.getWidth() - Tile.TILE_SIZE;
			respawnY = enterRight;
		}
		player.setX(respawnX);
		player.setY(respawnY);
	}

	@Override
	public void update() {
		bg.update();
		tileMap.update();
		camera.update();
		entityManager.update();
		if(player.getX() > tileMap.getWidth()) {
			moveToNextLevel(true);
		} else if(player.getX() + player.getBounds().getX() + player.getBounds().getWidth() < 0) {
			moveToNextLevel(false);
		}
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g, camera);
		tileMap.draw(g, camera);
		entityManager.draw(g, camera);
	}
}
