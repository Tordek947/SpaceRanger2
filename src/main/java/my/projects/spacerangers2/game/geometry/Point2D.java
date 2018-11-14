package my.projects.spacerangers2.game.geometry;

//import java.util.concurrent.atomic.AtomicLong;

public class Point2D {
//	private AtomicLong doubleX;
//	private AtomicLong doubleY;
	private double doubleX;
	private double doubleY;
	
	public Point2D(double x, double y) {
//		doubleX = new AtomicLong(Double.doubleToLongBits(x));
//		doubleY = new AtomicLong(Double.doubleToLongBits(y));
		doubleX = x;
		doubleY = y;
	}

	public Point2D(Point2D point) {
//		doubleX = new AtomicLong(point.getXAsLong());
//		doubleY = new AtomicLong(point.getYAsLong());
		doubleX = point.doubleX;
		doubleY = point.doubleY;
	}

	public Point2D() {
//		doubleX = new AtomicLong();
//		doubleY = new AtomicLong();
	}

//	private long getXAsLong() {
//		return doubleX.get();
//	}
//
//	private long getYAsLong() {
//		return doubleY.get();
//	}

	public void setNewValues(Point2D point) {
		this.doubleX = point.doubleX;
		this.doubleY = point.doubleY;
	}

	
	public void shiftBy(Vector2D...vectors) {
		for(Vector2D v : vectors) {
			increaseBy(v);
		}
	}

	private void increaseBy(Vector2D delta) {
//		long xResult = Double.doubleToLongBits(getX() + delta.x);
//		long yResult = Double.doubleToLongBits(getY() + delta.y);
//		doubleX.set(xResult);
//		doubleY.set(yResult);
		double xRes = doubleX + delta.x;
		double yRes = doubleY + delta.y;
		doubleX = xRes;
		doubleY = yRes;
	}	
	
	public double getY() {
//		return Double.longBitsToDouble(doubleY.get());
		return doubleY;
	}

	public double getX() {
//		return Double.longBitsToDouble(doubleX.get());
		return doubleX;
	}
	
	public void setY(double y) {
//		doubleY.set(Double.doubleToLongBits(y));
		this.doubleY = y;
	}

	public void setX(double x) {
//		doubleX.set(Double.doubleToLongBits(x));
		this.doubleX = x;
	}
	
	
	public static double distance(Point2D first, Point2D second) {
		return Math.hypot(second.getX() - first.getX(), second.getY() - first.getY());
	}
	

	public static Point2D between(Point2D first, Point2D second) {
		return new Point2D((first.getX() + second.getX())/2, (first.getY() + second.getY())/2);
	}
	
	
}
