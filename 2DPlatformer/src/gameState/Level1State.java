package gameState;

import java.awt.Graphics2D;

import assets.Assets;
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
		tileMap = new TileMap("Resources/Maps/testLevel2.tilemap");
		bg = new Background(Assets.BLANK_BG);
		camera = new Camera(gameStateManager, 0,0,1);
		camera.setTarget(500, 200);
		gravityScale = 0.5;
		
		player = new Player(gameStateManager, 32, 96);
		camera.setFocusedEntity(player);
	}

	@Override
	public void update() {
		bg.update();
		tileMap.update();
		camera.update();
		player.update();
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g, camera);
		tileMap.draw(g, camera);
		player.draw(g, camera);
	}
}
