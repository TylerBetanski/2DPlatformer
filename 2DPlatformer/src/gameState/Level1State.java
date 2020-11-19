package gameState;

import java.awt.Graphics2D;

import assets.Assets;
import entity.EntityManager;
import entity.creatures.enemies.Ghoul;
import entity.creatures.enemies.Wolfman;
import entity.creatures.player.Player;
import gfx.Background;
import gfx.Camera;
import tiles.TileMap;

public class Level1State extends LevelState {
	Player player;

	public Level1State(GameStateManager gameStateManager) {
		super(gameStateManager);
		init();
	}

	@Override
	public void init() {
		tileMap = new TileMap("Resources/Maps/caveLevel.tilemap");
		bg = new Background(Assets.BLANK_BG);
		camera = new Camera(gameStateManager, 0,128,5);
		camera.setTarget(500, 200);
		gravityScale = 22.5;
		
		player = new Player(gameStateManager, 32, 240);
		entityManager = new EntityManager(gameStateManager, player);
		entityManager.addEntity(new Wolfman(gameStateManager, 192, 240));
		entityManager.addEntity(new Wolfman(gameStateManager, 432, 240));
		entityManager.addEntity(new Wolfman(gameStateManager, 144, 64));
		entityManager.addEntity(new Wolfman(gameStateManager, 256, 64));
		entityManager.addEntity(new Wolfman(gameStateManager, 368, 64));
		entityManager.addEntity(new Wolfman(gameStateManager, 480, 64));
		entityManager.addEntity(new Ghoul(gameStateManager, 192, 160));
		entityManager.addEntity(new Ghoul(gameStateManager, 432, 160));
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
