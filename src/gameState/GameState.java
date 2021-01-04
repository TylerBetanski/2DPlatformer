package gameState;

import java.awt.Graphics2D;

public abstract class GameState {
	protected GameStateManager gameStateManager;
	
	public GameState(GameStateManager gameStateManager) {
		this.gameStateManager = gameStateManager;
	}
	
	public abstract void init();
	public abstract void reload();
	public abstract void update();
	public abstract void draw(Graphics2D g);
}
