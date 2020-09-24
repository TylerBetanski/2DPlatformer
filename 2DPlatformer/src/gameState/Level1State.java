package gameState;

import java.awt.Graphics2D;

import tileMap.TileMap;

public class Level1State extends GameState {
	
	private TileMap tileMap;

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
		
	}

	@Override
	public void draw(Graphics2D g) {
		tileMap.draw(g);
	}

}
