package gameState;

import java.awt.Graphics2D;

import assets.Assets;
import gfx.Background;
import tileMap.TileMap;

public class Level1State extends GameState {
	
	private TileMap tileMap;
	
	private Background bg = Assets.GRADIENT_BG;

	public Level1State(GameStateManager gameStateManager) {
		super(gameStateManager);
		init();
	}

	@Override
	public void init() {
		tileMap = new TileMap("Resources/Maps/testLevel.lvlData");
	}

	@Override
	public void update() {
		bg.update();
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		tileMap.draw(g);
	}

}
