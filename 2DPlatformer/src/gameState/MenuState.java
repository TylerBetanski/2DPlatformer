package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import assets.Assets;
import gfx.Background;
import input.Keys;

public class MenuState extends GameState {
	private Background bg;
	private int currentChoice = 0;
	private String[] options = {
			"Start",
			"Help",
			"Quit"
	};

	private Color titleColor;
	private Font titleFont;

	private Font font;

	public MenuState(GameStateManager gameStateManager) {
		super(gameStateManager);
		bg = new Background(Assets.WATER_BG);
		bg.setVector(-0.5);

		titleColor = Color.BLACK;
		titleFont = new Font("Century Gothic", Font.PLAIN, 15);
		font = new Font("Arial", Font.PLAIN, 12);
	}

	@Override
	public void init() {}

	@Override
	public void update() {
		bg.update();
		handleInput();
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
		if(Keys.isPressed(Keys.ENTER)) 
			select();
		if(Keys.isPressed(Keys.UP)) {
			if(currentChoice > 0)
				currentChoice--;
		}
		
		if(Keys.isPressed(Keys.DOWN)) {
			if(currentChoice < options.length - 1)
				currentChoice++;
		}
	}
	
	public void select() {
		System.out.println("You selected: " + options[currentChoice] + "!");
		if(currentChoice == 0) {
			gameStateManager.setState(GameStateManager.LEVEL1STATE);
		}
	}
}
