package my.projects.spacerangers2.game.concurrent;

import my.projects.spacerangers2.game.entities.Aimable;

public interface AimableModifiableList extends AimableWatchList {
	void addAimableEnemy(Aimable enemy);
	void setAimableUserShip(Aimable userShip);
}
