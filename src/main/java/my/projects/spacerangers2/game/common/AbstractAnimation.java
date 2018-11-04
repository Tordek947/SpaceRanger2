package my.projects.spacerangers2.game.common;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
/*
 * This class was created just for reducing code length, not for a polymorphism
 */
public abstract class AbstractAnimation {

	protected ImageView window;
	
	public AbstractAnimation() {
		window = new ImageView();
	}

	public void bindToParent(Pane parent) {
		parent.getChildren().add(window);
	}
	
	public boolean removeFromParent(Pane parent) {
		return parent.getChildren().remove(window);
	}

	public void setWindowPosition(double x, double y) {
		window.setX(x);
		window.setY(y);
	}
	
	public javafx.geometry.Bounds getBounds() {
		return window.getBoundsInParent();
	}

	public abstract void redraw();

	public abstract double getWidth();

	public abstract double getHeight();
		
}
