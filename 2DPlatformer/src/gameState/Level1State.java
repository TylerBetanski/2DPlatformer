package gameState;

import java.awt.Graphics2D;

import assets.Assets;
import entity.creatures.Creature;
import gfx.AnimatedTexture;
import gfx.Background;
import gfx.Camera;
import tileMap.TileMap;

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
		camera = new Camera(0,0,1);
		camera.setTarget(500, 0);
		
		demon = new Creature(gameStateManager, new AnimatedTexture(Assets.DEMON, false), 32, 32, 16, 16, 1, 1);
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
