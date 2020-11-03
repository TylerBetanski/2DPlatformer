package gfx;

public class AnimatedTexture extends Texture {
	private Flipbook flipbook;
	private int currentIndex = 0;
	
	public AnimatedTexture(Flipbook flipbook) {
		super(flipbook.getTextureAtIndex(0));
		this.flipbook = flipbook;
	}
	
	public AnimatedTexture(AnimatedTexture texture, boolean copyCurrentIndex) {
		super(texture.getFlipbook().getTextureAtIndex(texture.getCurrentIndex()));
		flipbook = texture.getFlipbook();
		if(!copyCurrentIndex)
			setImage(flipbook.getTextureAtIndex(0));
	}
	
	public AnimatedTexture(AnimatedTexture texture) {
		super(texture.getFlipbook().getTextureAtIndex(0));
		this.flipbook = texture.getFlipbook();
	}

	private int count = 0;
	@Override
	public void update() { // Called once every frame
		if(count >= flipbook.getFrameCount()[currentIndex]) {
			count = 0;
			
			setImage(flipbook.getTextureAtIndex(currentIndex));
			
			if(currentIndex == flipbook.getFrameCount().length - 1)
				currentIndex = 0;
			else
				currentIndex++;
		}
		count++;
	}
	
	public void reset() {
		count = 0;
	}
	
	public Flipbook getFlipbook() { return new Flipbook(flipbook); }
	public int getCurrentIndex() { return currentIndex; }

}
