package gameState;

import java.awt.Graphics2D;
import java.util.ArrayList;

import assets.Assets;
import main.SaveData;

public class GameStateManager {
	private ArrayList<GameState> gameStates;
	private int currentState;
	private SaveData saveData = new SaveData();
	
	public static final int MENUSTATE = 0;
	
	public GameStateManager() {
		gameStates = new ArrayList<GameState>();
		
		currentState = MENUSTATE;
		gameStates.add(new MenuState(this));
	}
	
	public void populateLevels() {
		for(int i = 0; i < Assets.LEVELS.size(); i++) {
			gameStates.add(Assets.LEVELS.get(i));
			((LevelState)gameStates.get(i + 1)).setIndex(i + 1);
		}
	}
	
	public void setState(int state) {
		currentState = state;
		gameStates.get(currentState).init();
	}
	
	public void update() {
		gameStates.get(currentState).update();
	}
	
	public void draw(Graphics2D g) {
		gameStates.get(currentState).draw(g);
	}
	
	public GameState getCurrentState() {
		return gameStates.get(currentState);
	}
	
	public SaveData getSaveData() { return saveData; }
}
