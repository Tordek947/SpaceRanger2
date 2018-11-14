package my.projects.spacerangers2.game.objects;

import my.projects.spacerangers2.game.common.AbstractAnimation;
import my.projects.spacerangers2.game.geometry.Bounds;
import my.projects.spacerangers2.game.geometry.Point2D;

public class BoundableSpaceObject<T extends AbstractAnimation> extends SpaceObject<T> implements Boundable {

	/**
	 * they have to be calculated according to specified animation and are binded to topLeftPosition as their origin.
	 */
	private Bounds bounds;
	
	public BoundableSpaceObject(T animation, Bounds bounds, Point2D ownTopLeftPosition) {
		super(animation, ownTopLeftPosition);
		this.bounds = bounds;
		bounds.setObjectCoordinatePoint(ownTopLeftPosition);
	}

	@Override
	public Bounds getBounds() {
		return bounds;
	}
	
	@Override
	public javafx.geometry.Bounds getApproximateBounds() {
		return animation.getBounds();
	}


}
