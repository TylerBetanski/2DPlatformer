package gfx;

import java.awt.image.BufferedImage;

import utils.Utils;

public class Spritesheet {
	private BufferedImage[] images;
	
	public Spritesheet(String loc, int xSize, int ySize) {
		BufferedImage image = Utils.loadImage(loc);
		images = new BufferedImage[(image.getWidth() / xSize) * (image.getHeight() / ySize)];
		int index = 0;
		for(int x = 0; x < image.getWidth() / xSize; x++) {
			for(int y = 0; y < image.getHeight() / ySize; y++) {
				images[index] = image.getSubimage(x * xSize, y * ySize, xSize, ySize);
				index++;
			}
		}
	}

	public BufferedImage[] getImages() { return images; }
	public BufferedImage getImageAtIndex(int index) { return images[index]; }
	public int getLength() { return images.length; }
}
