package my.projects.spacerangers2.game.geometry;

public class AxisSystem {
	private Axe oX;
	private Axe oY;
	private Point2D zeroPoint;
	
	private AxisSystem(Vector2D firstOrt, Vector2D secondOrt, Point2D oPoint) {
		zeroPoint = oPoint;
		oX = new Axe(firstOrt, zeroPoint);
		oY = new Axe(secondOrt, zeroPoint);
	}
	
	public void setNewOPoint(Point2D pointInCanonicalCoordinateSystem) {
		zeroPoint.setNewValues(pointInCanonicalCoordinateSystem);
	}
	
	public Point2D globalToLocalCoordinate(Point2D pointInGlobal) {
		double x = oX.getPointProjection(pointInGlobal);
		double y = oY.getPointProjection(pointInGlobal);
		return new Point2D(x,y);
	}
	
	public Point2D localToGlobalCoordinate(double localX, double localY) {
		Vector2D oXVectorComponent = oX.projectionToGlobalVectorComponent(localX);
		Vector2D oYVectorComponent = oY.projectionToGlobalVectorComponent(localY);
		Point2D result = new Point2D(zeroPoint);
		result.shiftBy(oXVectorComponent, oYVectorComponent);
		return result;
	}
	
	/**
	 * 
	 * @param firstDirection The second direction will be made by moving counter-clockwise
	 * @return the new AxisSystem instance
	 */
	public static AxisSystem ortogonal(Vector2D firstDirection, Point2D oPoint) {
		OrtogonalOrtSystem oos = OrtogonalOrtSystem.make(firstDirection);
		return new AxisSystem(oos.first, oos.second, oPoint);
	}
	
	private static class OrtogonalOrtSystem {
		public Vector2D first;
		public Vector2D second;
		
		private OrtogonalOrtSystem(Vector2D first, Vector2D second) {
			this.first = first;
			this.second = second;
		}

		public static OrtogonalOrtSystem make(Vector2D firstDirection) {
			Vector2D first = Vector2D.ort(firstDirection);
			Vector2D second = Vector2D.ortogonal(first);
			return new OrtogonalOrtSystem(first, second);
		}

	}

}
