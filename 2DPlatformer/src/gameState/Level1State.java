package gameState;

import java.awt.Graphics2D;

import assets.Assets;
import entity.creatures.Creature;
import gfx.AnimatedTexture;
import gfx.Background;
import gfx.Camera;
import tiles.TileMap;

public class Level1State extends LevelState {
	Creature demon;

	public Level1State(GameStateManager gameStateManager) {
		super(gameStateManager);
		init();
	}

	@Override
	public void init() {
		tileMap = new TileMap("Resources/Maps/testLevel.tilemap");
		bg = new Background(Assets.WATER_BG);
		camera = new Camera(gameStateManager, 100,0,1);
		//camera.setTarget(500, 200);
		gravityScale = 2;
		
		demon = new Creature(gameStateManager, new AnimatedTexture(Assets.DEMON, false), 32, 32, 1, 1, 1, true);
		camera.setFocusedEntity(demon);
	}

	@Override
	public void update() {
		bg.update();
		tileMap.update();
		camera.update();
		demon.update();
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g, camera);
		tileMap.draw(g, camera);
		demon.draw(g, camera);
	}
}
