package gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Texture {
	protected BufferedImage texture;
	
	public Texture(BufferedImage texture) {
		this.texture = texture;
	}
	
	public void draw(Graphics2D g, int x, int y, int width, int height) {
		g.drawImage(texture, x, y, width, height, null);
	}
	
	public void draw(Graphics2D g, int x, int y) {
		g.drawImage(texture, x, y, null);
	}
	
	public void update() {}
	
	protected void setTexture(BufferedImage image) {
		texture = image;
	}
	
	public int getWidth()
	{
		return texture.getWidth();
	}
	
	public int getHeight()
	{
		return texture.getHeight();
	}
}
