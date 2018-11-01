package my.projects.spacerangers2.game.geometry;

public class Vector {
	private double x;
	private double y;
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

//	public Vector() {
//		this.x = 0;
//		this.y = 0;
//	}

	public Vector(Vector vector) {
		this.x = vector.getX();
		this.y = vector.getY();
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void increaseBy(Vector delta) {
		this.x += delta.x;
		this.y += delta.y;
	}
	
	
}
