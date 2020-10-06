package gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Texture {
	protected BufferedImage texture;
	
	public Texture(BufferedImage texture) {
		this.texture = texture;
	}
	
	public Texture(Texture texture) {
		this.texture = texture.getTexure();
	}
	
	public void draw(Graphics2D g, int x, int y, int width, int height) {
		g.drawImage(texture, x, y, width, height, null);
	}
	
	public void draw(Graphics2D g, int x, int y) {
		g.drawImage(texture, x, y, null);
	}
	
	public void update() {}
	
	public BufferedImage getTexure() { return texture; }
	protected void setTexture(BufferedImage image) { texture = image; }
	protected void setTexture(Texture texture) { this.texture = texture.getTexure(); }
	public int getWidth() { return texture.getWidth(); }
	public int getHeight() { return texture.getHeight(); }
}
