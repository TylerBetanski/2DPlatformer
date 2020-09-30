package gameState;

import java.awt.Graphics2D;

import assets.Assets;
import tileMap.TileMap;

public class Level1State extends LevelState {

	public Level1State(GameStateManager gameStateManager) {
		super(gameStateManager);
		init();
	}

	@Override
	public void init() {
		tileMap = new TileMap("Resources/Maps/testLevel.lvlData");
		bg = Assets.GRADIENT_BG;
	}

	@Override
	public void update() {
		bg.update();
		tileMap.update();
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		tileMap.draw(g);
	}

}
