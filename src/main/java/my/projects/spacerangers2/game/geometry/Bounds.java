package my.projects.spacerangers2.game.geometry;

public abstract class Bounds {

	/*
	 * This point indicates in global axis system to the centre of the local zero of a coordinate system 
	 * first primitive in the primitive list,
	 * whose elements unification defines the whole bounds
	 */
	private Vector localOriginPoint;
	
	public void setInitialCentralPoint(Vector point) {
		localOriginPoint = point;
	}
}
