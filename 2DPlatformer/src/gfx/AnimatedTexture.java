package gfx;


public class AnimatedTexture extends Texture {
	private Flipbook flipbook;
	private int currentIndex = 0;
	
	public AnimatedTexture(Flipbook flipbook) {
		super(flipbook.getFrames()[0]);
		this.flipbook = flipbook;
	}
	
	private int count = 0;
	@Override
	public void update() { // Called once every frame
		count++;
		if(count >= flipbook.getFrameCount()[currentIndex]) {
			count = 0;
			if(currentIndex == flipbook.getFrameCount().length - 1)
				currentIndex = 0;
			else
				currentIndex++;
			
			setTexture(flipbook.getFrames()[currentIndex]);
		}
	}

}
