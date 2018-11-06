package my.projects.spacerangers2.game.concurrent;

import java.util.Iterator;
import java.util.Optional;

import my.projects.spacerangers2.game.entities.Aimable;

public interface AimableWatchList {
	Iterator<Aimable> observeEnemies();
	Optional<Aimable> getUserShip();
	void remove(Aimable element);
}
