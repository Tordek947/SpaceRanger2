package my.projects.spacerangers2.game.common;

import javafx.scene.image.Image;

public class SpriteAnimation extends AbstractAnimation {
	private Image[] images;
	private int currentImg;
	private int imageCount;

	public SpriteAnimation() {
		images = null;
		currentImg = -1;
		imageCount = 0;
	}

	protected SpriteAnimation(Image[] images) {
		super();
		setImages(images);	
		currentImg = imageCount/2;
	}

	public void setImages(Image[] images) {
		this.images = images;
		imageCount = images.length;
	}
	
	protected SpriteAnimation(Image[] images, int initialImg) {
		super();
		setImages(images);
		currentImg = initialImg;
	}

	public void flipForward() {
		currentImg++;
		if (currentImg == imageCount) {
			currentImg = 0;
		}
	}

	public void flipBack() {
		currentImg--;
		if (currentImg < 0) {
			currentImg = imageCount-1;
		}
	}

	public int getFlipCount() {
		return images.length;
	}

	public boolean isFirstFlip() {
		return currentImg == 0;
	}

	public boolean isLastFlip() {
		return currentImg == imageCount-1;
	}

	public boolean isCentralFlip() {
		return currentImg == imageCount/2;
	}

	@Override
	public void redraw() {
		window.setImage(images[currentImg]);
	}

	@Override
	public double getWidth() {
		return images[0].getWidth();
	}

	@Override
	public double getHeight() {
		return images[0].getHeight();
	}
}
