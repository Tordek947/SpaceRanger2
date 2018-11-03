package my.projects.spacerangers2.game.objects;

import my.projects.spacerangers2.game.geometry.Point2D;

public interface Positionable {
	Point2D getCentrePosition();
	void setCentrePosition(Point2D position);
	//Vector getLeftTopPosition();
	double getLeft();
	double getTop();
	void setLeftTopPosition(Point2D position);
}
