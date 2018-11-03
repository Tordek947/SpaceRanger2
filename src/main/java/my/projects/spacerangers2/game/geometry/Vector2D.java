package my.projects.spacerangers2.game.geometry;

public class Vector2D {
	public double x;
	public double y;
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double length() {
		return Math.hypot(x, y);
	}
	
	public double multiplyScalar(Vector2D other) {
		return x*other.x + y*other.y;
	}
	
	public static Vector2D scale(Vector2D origin, double scale) {
		return new Vector2D(origin.x*scale, origin.y*scale);
	}
	
	public static Vector2D ortFromPoints(Point2D startPoint, Point2D endPoint) {
		double x = endPoint.getX() - startPoint.getX();
		double y = endPoint.getY() - startPoint.getY();
		double length = Math.hypot(x, y);
		return new Vector2D(x/length, y/length);
	}
	
	public static Vector2D ort(Vector2D origin) {
		double length = origin.length();
		return new Vector2D(origin.x/length, origin.y/length);
	}
	
	public static Vector2D fromPoints(Point2D startPoint, Point2D endPoint) {
		return new Vector2D(endPoint.getX() - startPoint.getX(), endPoint.getY() - startPoint.getY());
	}

	public static Vector2D ortogonal(Vector2D first) {
		return new Vector2D(-first.y, first.x);
	}

}
