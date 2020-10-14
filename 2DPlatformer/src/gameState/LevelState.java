package gameState;

import gfx.Background;
import gfx.Camera;
import tiles.TileMap;

public abstract class LevelState extends GameState{

	protected TileMap tileMap;
	protected Background bg;
	protected Camera camera;
	protected double gravityScale;
	
	public LevelState(GameStateManager gameStateManager) {
		super(gameStateManager);
	}
	
	public TileMap getTileMap() { return tileMap; }
	public Background getBackground() { return bg; }
	public Camera getCamera() { return camera; }
	public double getGravityScale() { return gravityScale; }
	public void setBackground(Background bg) { this.bg = bg; }
}
