package gameState;

import gfx.Background;
import tileMap.TileMap;

public abstract class LevelState extends GameState{

	protected TileMap tileMap;
	protected Background bg;
	
	public LevelState(GameStateManager gameStateManager) {
		super(gameStateManager);
	}
	
	public TileMap getTileMap() { return tileMap; }
	public Background getBackground() { return bg; }
	public void setBackground(Background bg) { this.bg = bg; }
}
