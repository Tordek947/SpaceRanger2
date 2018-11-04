package my.projects.spacerangers2.game.common;

import java.util.concurrent.atomic.AtomicInteger;

import javafx.scene.image.Image;

public class SpriteAnimation extends AbstractAnimation {
	private Image[] images;
	private AtomicInteger currentImg;
	private int imageCount;

	public SpriteAnimation() {
		images = null;
		currentImg = new AtomicInteger(-1);
		imageCount = 0;
	}

	protected SpriteAnimation(Image[] images) {
		super();
		setImages(images);	
		currentImg = new AtomicInteger(imageCount/2);
	}

	public void setImages(Image[] images) {
		this.images = images;
		imageCount = images.length;
	}
	
	protected SpriteAnimation(Image[] images, int initialImg) {
		super();
		setImages(images);
		currentImg = new AtomicInteger(initialImg);
	}

	public void flipForward() {
		if (currentImg.get() == imageCount-1) {
			currentImg.set(0);
		} else {
			currentImg.incrementAndGet();
		}
	}

	public void flipBack() {
		if (currentImg.get() == 0) {
			currentImg.set(imageCount-1);
		} else {
			currentImg.decrementAndGet();
		}
	}

	public int getFlipCount() {
		return images.length;
	}

	public boolean isFirstFlip() {
		return currentImg.get() == 0;
	}

	public boolean isLastFlip() {
		return currentImg.get() == imageCount-1;
	}

	public boolean isCentralFlip() {
		return currentImg.get() == imageCount/2;
	}

	@Override
	public void redraw() {
		window.setImage(images[currentImg.get()]);
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
