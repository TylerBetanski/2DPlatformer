package gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;
import utils.Utils;

public class Background {
	private BufferedImage image;

	private double x, y, dx, dy;
	private double moveScale;

	public Background(String loc, double ms) {
		image = Utils.loadImage(loc);
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
		g.drawImage(image, (int)x, (int)y, null);
		
		// Tile the Background
		if(x < 0)
			g.drawImage(image, (int)x + image.getWidth(), (int)y, null );
		if(x + image.getWidth() < 0)
			x = GamePanel.WIDTH;
		if(x > 0)
			g.drawImage(image, (int)x - GamePanel.WIDTH, (int)y, null );
		if(x > GamePanel.WIDTH)
			x = 0;
		if(y < 0)
			g.drawImage(image, (int)x, (int)y + GamePanel.HEIGHT, null );
		if(y > GamePanel.HEIGHT)
			y = 0;
		if(y > 0)
			g.drawImage(image, (int)x, (int)y - GamePanel.HEIGHT, null );
		if(y + image.getHeight() < 0)
			y = GamePanel.WIDTH;
	}
}
