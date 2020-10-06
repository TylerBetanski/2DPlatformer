package gameState;

import java.awt.Graphics2D;

import assets.Assets;
import entity.creatures.Creature;
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
		bg = Assets.WATER_BG;
		camera = new Camera(0,0);
		
		demon = new Creature(gameStateManager, Assets.DEMON, 0, 0, 0, 0, null, false, 0, 1);
	}

	@Override
	public void update() {
		bg.update();
		tileMap.update();
		demon.update();
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		tileMap.draw(g, camera);
		demon.draw(g, camera);
	}

}
