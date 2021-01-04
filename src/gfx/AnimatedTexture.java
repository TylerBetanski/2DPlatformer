package gfx;

public class AnimatedTexture extends Texture {
	private Flipbook flipbook;
	private int[] xOrigins;
	private int[] yOrigins;
	private int currentIndex = 0;

	public AnimatedTexture(Flipbook flipbook) {
		super(flipbook.getTextureAtIndex(0));
		xOrigins = new int[flipbook.getTextures().length];
		yOrigins = new int[flipbook.getTextures().length];
		this.flipbook = flipbook;
	}

	public AnimatedTexture(AnimatedTexture texture, boolean copyCurrentIndex) {
		super(texture.getFlipbook().getTextureAtIndex(texture.getCurrentIndex()));
		flipbook = texture.getFlipbook();
		xOrigins = new int[flipbook.getTextures().length];
		yOrigins = new int[flipbook.getTextures().length];
		if(!copyCurrentIndex)
			setImage(flipbook.getTextureAtIndex(0));
	}

	public AnimatedTexture(AnimatedTexture texture) {
		super(texture.getFlipbook().getTextureAtIndex(0));
		this.flipbook = texture.getFlipbook();
		xOrigins = new int[flipbook.getTextures().length];
		yOrigins = new int[flipbook.getTextures().length];
	}

	public void setOrigin(int[] xOrigins, int[] yOrigins) {
		for(int i = 0; i < Math.min(xOrigins.length, yOrigins.length); i++) {
			this.xOrigins[i] = xOrigins[i];
			this.yOrigins[i] = yOrigins[i];
		}
		originX = xOrigins[0];
		originY = yOrigins[0];
	}

	private int count = 0;
	@Override
	public void update() { // Called once every frame
		if(count == flipbook.getFrameCount()[currentIndex]) {
			count = 0;

			if(currentIndex == flipbook.getTextures().length - 1)
				currentIndex = 0;
			else
				currentIndex++;

			originX = xOrigins[currentIndex];
			originY = yOrigins[currentIndex];
			setImage(flipbook.getTextureAtIndex(currentIndex));

		}
		count++;

	}

	@Override
	public void reset() {
		count = 0;
		currentIndex = 0;
		originX = xOrigins[currentIndex];
		originY = yOrigins[currentIndex];
		inverted = false;
		setImage(flipbook.getTextureAtIndex(currentIndex));
	}

	@Override
	public boolean animationEnded() {	

		return (currentIndex == flipbook.getTextures().length - 1
				&& count == flipbook.getFrameCount()[currentIndex]);
	}
	
	@Override
	public int getCount() {return count;}
	@Override
	public int getCurrentIndex() { return currentIndex; }
	
	@Override
	public void setCount(int count) {this.count = count;}
	@Override
	public void setCurrentIndex(int index) {currentIndex = index;}

	public Flipbook getFlipbook() { return new Flipbook(flipbook); }


}
