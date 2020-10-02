package gfx;

import java.awt.Graphics2D;

import main.GamePanel;
import utils.Utils;

public class Background {
	private Texture texture;

	private double x, y, vector;
	private double moveScale;

	public Background(String loc, double ms) {
		texture = new Texture(Utils.loadImage(loc));
		moveScale = ms;
	}

	public void setPosition(double x, double y) {
		this.x = (x * moveScale) % GamePanel.WIDTH;
		this.y = (y * moveScale) % GamePanel.HEIGHT;
	}

	public void setVector(double vector) {
		this.vector = vector;
	}

	public void update() {
		texture.update();
		x += vector;
	}

	public void draw(Graphics2D g) {
		texture.draw(g, (int)x, (int)y);
		// Tile the Background
		if(x > 0)
			texture.draw(g, (int)x - texture.getWidth(), (int)y);
		if(x > texture.getWidth())
			x = 0;
		
		if(x < 0)
			texture.draw(g, (int)x + texture.getWidth(), (int)y);
		if(x < -texture.getWidth())
			x = texture.getWidth();
	}
}
