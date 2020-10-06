package gameState;

import gfx.Background;
import gfx.Camera;
import tileMap.TileMap;

public abstract class LevelState extends GameState{

	protected TileMap tileMap;
	protected Background bg;
	protected Camera camera;
	
	public LevelState(GameStateManager gameStateManager) {
		super(gameStateManager);
	}
	
	public TileMap getTileMap() { return tileMap; }
	public Background getBackground() { return bg; }
	public Camera getCamera() { return camera; }
	public void setBackground(Background bg) { this.bg = bg; }
}
