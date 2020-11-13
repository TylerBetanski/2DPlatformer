package gameState;

import java.awt.Graphics2D;

import assets.Assets;
import entity.EntityManager;
import entity.creatures.enemies.Wolfman;
import entity.creatures.player.Player;
import gfx.Background;
import gfx.Camera;
import tiles.TileMap;

public class Level1State extends LevelState {
	EntityManager entityManager;
	Player player;
	Wolfman wolfman;

	public Level1State(GameStateManager gameStateManager) {
		super(gameStateManager);
		init();
	}

	@Override
	public void init() {
		tileMap = new TileMap("Resources/Maps/caveLevel.tilemap");
		bg = new Background(Assets.BLANK_BG);
		camera = new Camera(gameStateManager, 0,0,5);
		camera.setTarget(500, 200);
		gravityScale = 22.5;
		
		player = new Player(gameStateManager, 32, 96);
		entityManager = new EntityManager(player);
		wolfman = new Wolfman(gameStateManager, 48, 96);
		entityManager.addEntity(wolfman);
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
