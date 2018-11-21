package my.projects.spacerangers2.game.objects;

import javafx.scene.layout.Pane;
import my.projects.spacerangers2.game.common.AbstractAnimation;
import my.projects.spacerangers2.game.geometry.Point2D;
import my.projects.spacerangers2.game.geometry.Vector2D;

public abstract class SpaceObject<T extends AbstractAnimation> implements Visible, Positionable, Performable, Movable {
	
	private Point2D topLeftPosition;
	protected T animation;
	private Pane representingStage;
	private boolean isOnStage;
	
	
	public SpaceObject(T animation, Point2D topLeftPosition) {
		this.topLeftPosition = topLeftPosition;
		this.animation = animation;
		this.isOnStage = false;
	}
	public SpaceObject(T animation) {
		this.topLeftPosition = new Point2D();
		this.animation = animation;
		this.isOnStage = false;
	}

	public void setRepresentingStage(Pane representingStage) {
		this.representingStage = representingStage;
	}
	
	@Override
	public Point2D getCenterPosition() {
		double x = topLeftPosition.getX() + animation.getWidth()/2;
		double y = topLeftPosition.getY() + animation.getHeight()/2;
		Point2D position = new Point2D(x,y);
		return position;
	}

	@Override
	public void setCenterPosition(Point2D position) {
		topLeftPosition.setX((position.getX() - animation.getWidth()/2));
		topLeftPosition.setY((position.getY() - animation.getHeight()/2));
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
	public void setLeftTopPosition(Point2D position) {
		topLeftPosition.setNewValues(position);
	}	
	
	@Override
	public void setLeftTopPosition(double left, double top) {
		topLeftPosition.setX(left);
		topLeftPosition.setY(top);
	}
	
	@Override
	public void setXCenter(double xCenter) {
		topLeftPosition.setX(xCenter - animation.getWidth()/2);
	}
	
	@Override
	public void setBottom(double bottom) {
		topLeftPosition.setY(bottom - animation.getHeight());
	}

	@Override
	public double getLeftRightCenter() {
		return topLeftPosition.getX() +  animation.getWidth()/2;
	}

	/**
	 * Binds the animation to the specified Pane
	 */
	@Override
	public void show() {
		if (representingStage != null && !isOnStage) {
			isOnStage = true;
			animation.bindToParent(representingStage);
		}
	}

	/**
	 * Removes the animation from the specified Pane
	 */
	@Override
	public void hide() {
		if (representingStage != null && isOnStage) {
			isOnStage = false;
			animation.removeFromParent(representingStage);
		}
	}
	
	@Override
	public void moveByVector(Vector2D delta) {
		topLeftPosition.shiftBy(delta);
	}
	
	@Override
	public void perform() {
		animation.setWindowPosition(topLeftPosition.getX(), topLeftPosition.getY());
		animation.redraw();
	}
	
}
