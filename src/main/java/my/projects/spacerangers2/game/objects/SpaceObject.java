package my.projects.spacerangers2.game.objects;

import javafx.scene.layout.Pane;
import my.projects.spacerangers2.game.common.AbstractAnimation;
import my.projects.spacerangers2.game.geometry.Bounds;
import my.projects.spacerangers2.game.geometry.Vector;

public abstract class SpaceObject<T extends AbstractAnimation> implements Boundable, Visible, Positionable, Performable, Movable {
	private Vector topLeftPosition;
	private T animation;
	private Pane representingStage;
	private boolean isOnStage;
	
	private Bounds bounds;//they have to be calculated according to specified animation and are binded to TopLeftPosition as their origin.

	public SpaceObject(T animation, Pane representingStage) {
		this.animation = animation;
		this.representingStage = representingStage;
		this.isOnStage = false;
		this.bounds = initializeBounds();
	}

	/**
	 * Calculate Bounds (depending on particular images in animation)
	 * @return calculated bounds
	 */
	protected abstract Bounds initializeBounds();

	@Override
	public Vector getCentrePosition() {
		double x = topLeftPosition.getX() + animation.getWidth()/2;
		double y = topLeftPosition.getY() + animation.getHeight()/2;
		Vector position = new Vector(x,y);
		return position;
	}

	@Override
	public void setCentrePosition(Vector position) {
		topLeftPosition.setX(position.getX() - animation.getWidth()/2);
		topLeftPosition.setY(position.getY() - animation.getHeight()/2);
	}
	
	@Override
	public double getLeft() {
		return topLeftPosition.getX();
	}
	
	@Override
	public double getTop() {
		return topLeftPosition.getY();
	}

	@Override
	public void setLeftTopPosition(Vector position) {
		topLeftPosition = new Vector(position);
	}

	/**
	 * Binds the animation to the specified Pane
	 */
	@Override
	public void show() {
		if (!isOnStage) {
			isOnStage = true;
			animation.bindToParent(representingStage);
		}
	}

	/**
	 * Removes the animation from the specified Pane
	 */
	@Override
	public void hide() {
		if (isOnStage) {
			isOnStage = false;
			animation.removeFromParent(representingStage);
		}
	}

	@Override
	public Bounds getBounds() {
		return bounds;
	}

	@Override
	public void moveByVector(Vector delta) {
		topLeftPosition.increaseBy(delta);
	}
	
}
