package my.projects.spacerangers2.game.geometry;

public class Circle implements GeometricalPrimitive {
	
	private Point2D centralPoint;
	private double radius;
	
	
	public Circle( double radius) {
		centralPoint = new Point2D();
		this.radius = radius;
	}

	@Override
	public boolean contains(Point2D point) {
		if (Point2D.distance(centralPoint, point) < radius) {
			return true;
		}
		return false;
	}

	@Override
	public boolean intersects(GeometricalPrimitive other) {
		if (other instanceof Circle) {
			return intersectsCircle((Circle)other);
		}
		return false;
	}

	private boolean intersectsCircle(Circle other) {
		if (Point2D.distance(centralPoint, other.centralPoint) < radius + other.radius) {
			return true;
		}
		return false;
	}

	@Override
	public void setCentralPointInGlobal(Point2D point) {
		centralPoint.setNewValues(point);
	}
}
