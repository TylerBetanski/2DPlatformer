package gameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import input.Keys;
import tileMap.Background;

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
		bg = new Background("/Backgrounds/menuBG.png", 1);
		bg.setVector(-0.5, 0);

		titleColor = Color.BLACK;
		titleFont = new Font("Century Gothic", Font.PLAIN, 20);
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
		g.drawString("Independent Study", 80, 70);

		// Draw Menu options
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice)
				g.setColor(Color.RED);
			else
				g.setColor(Color.BLACK);
			g.setFont(font);
			g.drawString(options[i], 145, 140 + i * 15);
		}
	}
	
	public  void handleInput() {
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
	}
	
	public void select() {
		System.out.println("You selected: " + options[currentChoice] + "!");
	}
}
