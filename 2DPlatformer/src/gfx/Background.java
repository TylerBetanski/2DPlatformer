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
	
	public Background(Background background) {
		texture = background.getTexture();
		x = 0;
		y = 0;
		moveScale = background.getMoveScale();
	}

	public void setPosition(double x, double y) {
		this.x = (x * moveScale) % GamePanel.WIDTH;
		this.y = (y * moveScale) % GamePanel.HEIGHT;
	}

	public void setVector(double vector) {
		this.vector = vector;
	}

	public void update() {
		//System.out.println("Background: " + x + ", " + y);
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
	
	public void draw(Graphics2D g, Camera camera) {
		texture.draw(g, (int)x - (int)camera.getX(), (int)y);
		
		// Tile the Background	
		if(x + texture.getWidth() < camera.getX() + GamePanel.WIDTH) {
			texture.draw(g, (int)x - (int)camera.getX() + texture.getWidth(), (int)y);
		}
		if(x > camera.getX()) {
			texture.draw(g, (int)x - (int)camera.getX() - texture.getWidth(), (int)y);
		}
		
		if(x + 2*texture.getWidth() < camera.getX() + GamePanel.WIDTH) {
			x = camera.getX() + GamePanel.WIDTH;
		}
		if(x - texture.getWidth() > camera.getX()) {
			x = camera.getX();
		}
	}
	
	public Texture getTexture() { return new Texture(texture); }
	public double getX() { return x; }
	public double getY() { return y; }
	public double getMoveScale() { return moveScale; }
	public void setX(int x) {this.x = x; }
	public void setY(int y) {this.y = y; }
}
