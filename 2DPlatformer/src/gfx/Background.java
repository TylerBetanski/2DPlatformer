package gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;
import utils.Utils;

public class Background {
	private Texture texture;

	private double x, y, dx, dy;
	private double moveScale;

	public Background(String loc, double ms) {
		texture = new Texture(Utils.loadImage(loc));
		moveScale = ms;
	}

	public void setPosition(double x, double y) {
		this.x = (x * moveScale) % GamePanel.WIDTH;
		this.y = (y * moveScale) % GamePanel.HEIGHT;
	}

	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public void update() {
		x += dx;
		y += dy;
	}

	public void draw(Graphics2D g) {
		texture.draw(g, (int)x, (int)y);

		//Tile the Background
		if(x < 0)
			texture.draw(g, (int)x + GamePanel.WIDTH, (int)y);
		if(x + texture.getWidth() < 0)
			x = GamePanel.WIDTH;
		if(x > 0)
			texture.draw(g, (int)x - GamePanel.WIDTH, (int)y);
		if(x > GamePanel.WIDTH)
			x = 0;
		if(y < 0)
			texture.draw(g, (int)x, (int)y + GamePanel.HEIGHT);
		if(y > GamePanel.HEIGHT)
			y = 0;
		if(y > 0)
			texture.draw(g, (int)x, (int)y - GamePanel.HEIGHT);
		if(y + texture.getHeight() < 0)
			y = GamePanel.WIDTH;
		if(x < 0 && y < 0)
			texture.draw(g, (int)x + texture.getWidth(), (int)y + texture.getHeight());
		if(x < 0 && y - texture.getHeight() < 0)
			texture.draw(g, (int)x + texture.getWidth(), (int)y - texture.getHeight());
		if(x + texture.getWidth() > GamePanel.WIDTH && y < 0)
			texture.draw(g, (int)x - texture.getWidth(), (int)y - texture.getHeight());
		if(x + texture.getWidth() > GamePanel.WIDTH && y - texture.getHeight() < 0)
			texture.draw(g, (int)x - texture.getWidth(), (int)y - texture.getHeight());
		
	}
}
