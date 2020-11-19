package gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Texture {
	protected BufferedImage texture;
	protected BufferedImage invertedTexture;
	protected boolean inverted;
	protected int originX = 0;
	protected int originY = 0;

	public Texture(BufferedImage image) {
		BufferedImage tempImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				tempImage.setRGB(x, y, image.getRGB(x, y));
			}
		}
		this.texture = tempImage;
		this.invertedTexture = RenderEffect.invert(tempImage);
	}

	public Texture(Texture texture) {
		this.texture = texture.getImage();
		this.invertedTexture = RenderEffect.invert(this.texture);
	}

	public void draw(Graphics2D g, int x, int y, int width, int height) {
		BufferedImage drawTexture = texture;
		if(inverted) {
			drawTexture = invertedTexture;
		}
		
		g.drawImage(drawTexture, x - ((width / texture.getWidth()) * originX), y - ((height / texture.getHeight()) * originY), width, height, null);
	}

	public void draw(Graphics2D g, int x, int y) {
		draw(g, x, y, texture.getWidth(), texture.getHeight());
		
		//g.drawImage(texture, x - originX, y - originY, null);
	}
	
	public void setOrigin(int x, int y) {
		originX = x;
		originY = y;
	}
	
	public int getOriginX() { return originX; }
	public int getOriginY() { return originY; }
	
	public void update() {}
	
	public void reset() {inverted = false;}
	
	public boolean animationEnded() {return true;}
	
	public int getCount() {return 0;}
	public int getCurrentIndex() {return 0;}
	
	public void setCount(int count) {}
	public void setCurrentIndex(int index) {}
	
	public BufferedImage getImage() {
		BufferedImage tempImage = new BufferedImage(texture.getWidth(), texture.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for(int x = 0; x < texture.getWidth(); x++) {
			for(int y = 0; y < texture.getHeight(); y++) {
				tempImage.setRGB(x, y, texture.getRGB(x, y));
			}
		}
		return tempImage;
	}
	protected void setImage(BufferedImage image) {
		BufferedImage tempImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				tempImage.setRGB(x, y, image.getRGB(x, y));
			}
		}
		texture = tempImage; 
	}
	protected void setImage(Texture texture) { this.texture = texture.getImage();
												this.invertedTexture = RenderEffect.invert(this.texture); }
	public int getWidth() { return texture.getWidth(); }
	public int getHeight() { return texture.getHeight(); }
	public boolean isInverted() { return inverted; }
	public void invert(boolean inverted) { this.inverted = inverted; }
}
