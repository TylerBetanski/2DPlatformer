package gfx;

import java.awt.image.BufferedImage;

public class Flipbook {
	private BufferedImage[] frames;
	private int[] frameCount;
	
	public Flipbook(BufferedImage[] frames, int[] frameCount) {
		this.frames = frames;
		this.frameCount = frameCount;
	}
	
	public Flipbook(BufferedImage[] frames) {
		this.frames = frames;
		frameCount = new int[frames.length];
	}
	
	public BufferedImage[] getFrames() { return frames; }
	public int[] getFrameCount() { return frameCount; }

}
