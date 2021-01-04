package gameState;

import java.awt.Graphics2D;

import assets.Assets;
import entity.EntityManager;
import entity.creatures.player.Player;
import gfx.Background;
import gfx.Camera;
import tiles.TileMap;

public class Level1State extends LevelState {
	Player player;

	public Level1State(GameStateManager gameStateManager) {
		super(gameStateManager, "ruinsLevel", 128, 128, true);
		init();
	}

	@Override
	public void init() {
		tileMap = new TileMap("/Maps/ruinsLevel.tilemap");
		entityDataPath = "/Maps/ruinsLevel.entity";
		entityManager = new EntityManager(gameStateManager);
		savesPath = "/Maps/ruinsLevel.saves";
		loadEntities();
		entityManager.init(this);
		player = entityManager.getPlayer();
		bg = new Background(Assets.BLANK_BG);
		camera = new Camera(gameStateManager, 0,128,592);
		camera.setTarget(500, 200);
		gravityScale = 22.5;
		camera.setFocusedEntity(player);
	}

	@Override
	public void update() {
		bg.update();
		tileMap.update();
		camera.update();
		entityManager.update();
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g, camera);
		tileMap.draw(g, camera);
		entityManager.draw(g, camera);
	}
}
