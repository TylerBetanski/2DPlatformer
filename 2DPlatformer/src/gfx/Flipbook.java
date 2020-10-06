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

	public Texture[] getTextures() { return textures; }
	public Texture getTextureAtIndex(int index) { return textures[index]; }
	public int[] getFrameCount() { return frameCount; }

}
