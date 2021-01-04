package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import assets.Assets;
import gfx.Background;
import input.Keys;
import main.GamePanel;
import main.SaveData;
import utils.Utils;

public class MenuState extends GameState {
	private Background bg;
	private int currentChoice = 0;
	private String[] options = {
			"New Game",
			"Options",
			"Quit"
	};

	private Color titleColor;
	private Font titleFont;

	private Font font;
	private SaveData saveData;

	public MenuState(GameStateManager gameStateManager) {
		super(gameStateManager);
		init();
	}

	@Override
	public void init() {
		saveData = gameStateManager.getSaveData();
		
		bg = new Background(Assets.BLANK_BG);
		bg.setVector(-0.5);
		
		if(saveData.getGameStarted()) {
			options = new String[] {"Continue", "New Game", "Options", "Quit"};
		}

		titleColor = Color.BLACK;
		titleFont = new Font("Century Gothic", Font.PLAIN, 15);
		font = new Font("Arial", Font.PLAIN, 12);
	}

	@Override
	public void update() {
		bg.update();
		handleInput();
	}
	
	@Override
	public void reload() {
		
	}

	@Override
	public void draw(Graphics2D g) {
		// Draw Background
		bg.draw(g);

		// Draw Title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Independent Study", 22, 30);

		// Draw Menu options
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice)
				g.setColor(Color.RED);
			else
				g.setColor(Color.BLACK);
			g.setFont(font);
			g.drawString(options[i], 22, 60 + i * 20);
		}
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ACTION1)) 
			select();
		if(Keys.isPressed(Keys.UP)) {
			if(currentChoice > 0)
				currentChoice--;
		}
		
		if(Keys.isPressed(Keys.DOWN)) {
			if(currentChoice < options.length - 1)
				currentChoice++;
		}
		
		if(Keys.isPressed(Keys.ESCAPE)) {
			System.exit(0);
		}
	}
	
	public void select() {
		if(saveData.getGameStarted()) {
			if(currentChoice == 0) { // CONTINUE
				gameStateManager.setState(saveData.getCurrentLevelIndex());
			} else if(currentChoice == 1) { // NEW GAME
				saveData.reset();
				saveData.setGameStarted(true);
				saveData.writeSaveData();
				gameStateManager.setState(1);
			} else if(currentChoice == 2) { // OPTIONS
				
			} else if(currentChoice == 3) { // QUIT
				System.exit(0);
			}
		} else {
			if(currentChoice == 0) { // NEW GAME
				saveData.reset();
				saveData.setGameStarted(true);
				saveData.writeSaveData();
				gameStateManager.setState(1);
			} else if(currentChoice == 1) { // OPTIONS
				
			} else if(currentChoice == 2) { // QUIT
				System.exit(0);
			}
		}
	}
}
