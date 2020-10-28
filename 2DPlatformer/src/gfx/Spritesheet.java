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
	
	public Spritesheet(String loc) {
		BufferedImage image = Utils.loadImage(loc);
		String file = Utils.loadFileAsString("Resources" + loc.substring(0, loc.indexOf(".")) + ".spritedata");
		String[] tokens = file.split("\\R");
		images = new BufferedImage[tokens.length];
		for(int i = 0; i < tokens.length; i++) {
			System.out.println(i);
			System.out.println(tokens[i]);
			int startX = Integer.parseInt(tokens[i].split("/")[0].split(",")[0]);
			int startY = Integer.parseInt(tokens[i].split("/")[0].split(",")[1]);
			int width  = Integer.parseInt(tokens[i].split("/")[1].split(",")[0]);
			int height  = Integer.parseInt(tokens[i].split("/")[1].split(",")[1]);

			images[i] = image.getSubimage(startX, startY, width, height);
		}
	}

	public BufferedImage[] getImages() { return images; }
	public BufferedImage getImageAtIndex(int index) { return images[index]; }
	public int getLength() { return images.length; }
}
