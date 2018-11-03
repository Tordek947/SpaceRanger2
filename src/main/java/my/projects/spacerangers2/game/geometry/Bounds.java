package my.projects.spacerangers2.game.geometry;

import java.util.ArrayList;
import java.util.List;

public abstract class Bounds {

	private Point2D objectCoordinatePoint;
	private List<BoundsUnit> boundsList;
	
	public Bounds(Point2D objectCoordinatePoint) {
		this.objectCoordinatePoint = objectCoordinatePoint;
		boundsList = new ArrayList<>();
	}
	
	public void addPrimitive(Vector2D shiftToPrimitive, GeometricalPrimitive primitive) {
		primitive.setCentralPointInGlobal(objectCoordinatePoint);
		boundsList.add(new BoundsUnit(primitive, shiftToPrimitive));
	}
	
	public boolean intersects(Bounds other) {
		update();
		other.update();
		for(BoundsUnit bu : boundsList) {
			for(BoundsUnit otherBu : other.boundsList) {
				if (bu.intersects(otherBu)) {
					return true;
				}
			}
		}
		return false;
	}

	private void update() {
		for(BoundsUnit bu : boundsList) {
			bu.update(objectCoordinatePoint);
		}
	}
	
}
