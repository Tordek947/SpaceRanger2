package my.projects.spacerangers2.game.geometry;

public class Rectangular implements GeometricalPrimitive {

	private AxisSystem centralSystem;
	private double halfLength, halfWidth;
	
	private Rectangular(AxisSystem centralSystem, double length, double width) {
		this.centralSystem = centralSystem;
		this.halfLength = length/2;
		this.halfWidth = width/2;
	}

	@Override
	public boolean contains(Point2D point) {
		point = centralSystem.globalToLocalCoordinate(point);
		if (Math.abs(point.getX())< halfLength && Math.abs(point.getY()) < halfWidth) {
			return true;
		}
		return false;
	}

	@Override
	public boolean intersects(GeometricalPrimitive other) {
		if (other instanceof Rectangular) {
			return intersectsRectangular((Rectangular) other);
		}
		
		return false;
	}

	private boolean intersectsRectangular(Rectangular other) {
		if (containsVerticesOf(other) || other.containsVerticesOf(this)) {
			return true;
		}
		return false;
	}

	private boolean containsVerticesOf(Rectangular other) {
		Point2D[] vertices = other.getVerticesInGlobal();
		for(Point2D v : vertices) {
			if (contains(v)) {
				return true;
			}
		}
		return false;		
	}

	private Point2D[] getVerticesInGlobal() {
		Point2D[] vertices = new Point2D[4];
		vertices[0] = centralSystem.localToGlobalCoordinate(halfLength, halfWidth);
		vertices[1] = centralSystem.localToGlobalCoordinate(-halfLength, halfWidth);
		vertices[2] = centralSystem.localToGlobalCoordinate(-halfLength, -halfWidth);
		vertices[3] = centralSystem.localToGlobalCoordinate(halfLength, -halfWidth);
		return vertices;
	}

	@Override
	public void setCentralPointInGlobal(Point2D point) {
		centralSystem.setNewOPoint(point);
	}

	
	public static Rectangular fromPoints(Point2D topLeft, Point2D topRight, Point2D bottomLeft) throws IllegalArgumentException{
		if (topLeft.getX() > topRight.getX() || bottomLeft.getX() > topRight.getX() ||
				topLeft.getY() < bottomLeft.getY() || topRight.getY() < bottomLeft.getY()) {
			throw new IllegalArgumentException("Not proper points to make a rectangular");
		}
		Vector2D firstDirection = Vector2D.fromPoints(topLeft, topRight);
		double length = firstDirection.length();
		double width = Point2D.distance(topLeft, bottomLeft);
		Point2D center = Point2D.between(bottomLeft, topRight);
		AxisSystem axisSystem = AxisSystem.ortogonal(firstDirection, center);
		return new Rectangular(axisSystem, length, width);
	}

}
