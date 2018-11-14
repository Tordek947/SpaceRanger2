package my.projects.spacerangers2.game.objects;

import my.projects.spacerangers2.game.geometry.Point2D;

public interface Positionable {
	Point2D getCenterPosition();
	void setCenterPosition(Point2D position);
	double getLeftRightCenter();
	double getLeft();
	double getTop();
	void setLeftTopPosition(Point2D position);
	void setLeftTopPosition(double left, double top);
	void setXCenter(double xCenter);
	void setBottom(double bottom);
}
