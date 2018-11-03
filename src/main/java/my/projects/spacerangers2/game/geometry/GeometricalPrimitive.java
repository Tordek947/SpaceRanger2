package my.projects.spacerangers2.game.geometry;

public interface GeometricalPrimitive {
	boolean contains(Point2D point);
	boolean intersects(GeometricalPrimitive other);
	/**
	 * Must just rewrite values from point
	 * @param point
	 */
	void setCentralPointInGlobal(Point2D point);
}
