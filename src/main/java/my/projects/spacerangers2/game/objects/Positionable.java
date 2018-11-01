package my.projects.spacerangers2.game.objects;

import my.projects.spacerangers2.game.geometry.Vector;

public interface Positionable {
	Vector getCentrePosition();
	void setCentrePosition(Vector position);
	//Vector getLeftTopPosition();
	double getLeft();
	double getTop();
	void setLeftTopPosition(Vector position);
}
