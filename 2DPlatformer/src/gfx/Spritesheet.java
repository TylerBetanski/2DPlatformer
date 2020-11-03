package gfx;

import java.awt.image.BufferedImage;

import utils.Utils;

public class Spritesheet {
	private Texture[] textures;
	
	public Spritesheet(String loc, int xSize, int ySize) {
		BufferedImage image = Utils.loadImage(loc);
		textures = new Texture[(image.getWidth() / xSize) * (image.getHeight() / ySize)];
		int index = 0;
		for(int x = 0; x < image.getWidth() / xSize; x++) {
			for(int y = 0; y < image.getHeight() / ySize; y++) {
				textures[index] = new Texture(image.getSubimage(x * xSize, y * ySize, xSize, ySize));
				index++;
			}
		}
	}
	
	public Spritesheet(String loc) {
		BufferedImage image = Utils.loadImage(loc);
		String file = Utils.loadFileAsString("Resources" + loc.substring(0, loc.indexOf(".")) + ".spritedata");
		String[] tokens = file.split("\\R");
		textures = new Texture[tokens.length];
		for(int i = 0; i < tokens.length; i++) {
			int startX = Integer.parseInt(tokens[i].split("/")[0].split(",")[0]);
			int startY = Integer.parseInt(tokens[i].split("/")[0].split(",")[1]);
			int width  = Integer.parseInt(tokens[i].split("/")[1].split(",")[0]);
			int height  = Integer.parseInt(tokens[i].split("/")[1].split(",")[1]);

			textures[i] = new Texture(image.getSubimage(startX, startY, width, height));
		}
	}

	public Texture[] getTextures() { return textures; }
	public Texture getTextureAtIndex(int index) { return textures[index]; }
	public int getLength() { return textures.length; }
}
