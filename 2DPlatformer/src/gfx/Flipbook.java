package gfx;

public class Flipbook {
	private Texture[] textures;
	private int[] frameCount;

	public Flipbook(Spritesheet spriteSheet, int[] frameCount) {
		this.frameCount = frameCount;
		textures = new Texture[spriteSheet.getLength()];
		for(int i = 0; i < spriteSheet.getLength(); i++) {
			textures[i] = new Texture(spriteSheet.getImageAtIndex(i));
		}
	}

	public Flipbook(Spritesheet spriteSheet) {
		frameCount = new int[spriteSheet.getLength()];
		for(int i = 0; i < frameCount.length; i++) {
			frameCount[i] = 1;
		}
		textures = new Texture[spriteSheet.getLength()];
		for(int i = 0; i < spriteSheet.getLength(); i++) {
			textures[i] = new Texture(spriteSheet.getImageAtIndex(i));
		}
	}

	public Flipbook(Flipbook flipbook) {
		textures = flipbook.getTextures();
		frameCount = flipbook.getFrameCount();
	}

	public Texture[] getTextures() { 
		Texture[] temp = new Texture[textures.length];
		for(int i = 0; i < temp.length; i++) {
			temp[i] = new Texture(textures[i].getImage());
		}
		return temp; 
	}
	
	public Texture getTextureAtIndex(int index) {
		return new Texture(textures[index].getImage()); 
	}
	
	public int[] getFrameCount() { return frameCount; }

}
