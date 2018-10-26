package my.projects.spacerangers2.game.common;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class SpriteAnimation {
	private Image[] images;
	private int currentImg;
	private int imageCount;
	private ImageView window;
	
	public SpriteAnimation() {
		images = null;
		currentImg = -1;
		imageCount = 0;
		window = new ImageView();
	}

	public SpriteAnimation(Image[] images) {
		setImages(images);
		window = new ImageView();		
	}

	public void setImages(Image[] images) {
		this.images = images;
		imageCount = images.length;
		currentImg = imageCount/2;		
	}
	
	public SpriteAnimation(Image[] images, int initialImg) {
		this.images = images;
		imageCount = images.length;
		currentImg = initialImg;
		window = new ImageView();
	}

	public void bindToParent(Pane parent) {
		parent.getChildren().add(window);
	}

	public boolean removeFromParent(Pane parent) {
		return parent.getChildren().remove(window);
	}

	public void setWindowPosition(Vector position) {
		window.setX(position.getX());
		window.setY(position.getY());
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

	public void redraw() {
		window.setImage(images[currentImg]);
	}

	public double getWidth() {
		return images[0].getWidth();
	}

	public double getHeight() {
		return images[0].getHeight();
	}
}
