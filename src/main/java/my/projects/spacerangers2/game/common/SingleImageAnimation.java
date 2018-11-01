package my.projects.spacerangers2.game.common;

import javafx.scene.image.Image;

public class SingleImageAnimation extends AbstractAnimation {
	
	private Image image;

	public SingleImageAnimation() {
		super();
		image = null;
	}

	public SingleImageAnimation(Image image) {
		super();
		setImage(image);
	}


	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	public void redraw() {
		window.setImage(image);
	}

	@Override
	public double getWidth() {
		return image.getWidth();
	}

	@Override
	public double getHeight() {
		return image.getHeight();
	}

}
