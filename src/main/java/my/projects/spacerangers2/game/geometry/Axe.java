package my.projects.spacerangers2.game.geometry;

public class Axe {
	
	private Vector2D ort;
	private Point2D zeroPoint;
	
	public Axe(Vector2D ort, Point2D zeroPoint) {
		this.ort = ort;
		this.zeroPoint = zeroPoint;
	}
	
	public double getPointProjection(Point2D point) {
		return Vector2D.fromPoints(zeroPoint, point).multiplyScalar(ort);
	}

	public void setZeroPoint(Point2D zeroPoint) {
		this.zeroPoint = zeroPoint;
	}
	
	public Vector2D projectionToGlobalVectorComponent(double projection) {
		return Vector2D.scale(ort, projection);
	}
	
}
