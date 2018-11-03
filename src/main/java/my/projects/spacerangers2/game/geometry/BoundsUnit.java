package my.projects.spacerangers2.game.geometry;

public class BoundsUnit {
	private GeometricalPrimitive primitive;
	private Vector2D shiftToPrimitiveLocalCentre;
	
	public BoundsUnit(GeometricalPrimitive primitive, Vector2D shiftToPrimitiveLocalCentre) {
		this.primitive = primitive;
		this.shiftToPrimitiveLocalCentre = shiftToPrimitiveLocalCentre;
	}

	public void update(Point2D objectCoordinatePoint) {
		Point2D primitiveCenter = new Point2D(objectCoordinatePoint);
		primitiveCenter.shiftBy(shiftToPrimitiveLocalCentre);
		primitive.setCentralPointInGlobal(primitiveCenter);
	}
	
	public boolean intersects(BoundsUnit otherUnit) {
		return primitive.intersects(otherUnit.primitive);
	}
	
	
}
