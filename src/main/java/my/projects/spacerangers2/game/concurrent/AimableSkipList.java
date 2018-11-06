package my.projects.spacerangers2.game.concurrent;

import java.util.Iterator;
import java.util.Optional;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Consumer;

import my.projects.spacerangers2.game.entities.Aimable;

public class AimableSkipList implements AimableList {

	private SortedSet<Aimable> enemiesSet;
	private Optional<Aimable> userShip;
	
	public AimableSkipList() {
		enemiesSet = new ConcurrentSkipListSet<Aimable>();
		userShip = Optional.empty();
	}
	
	@Override
	public Iterator<Aimable> observeEnemies() {
		return enemiesSet.iterator();
	}

	@Override
	public Optional<Aimable> getUserShip() {
		return userShip;
	}

	@Override
	public void remove(Aimable element) {
		if (userShip.isPresent() && element == userShip.get()) {
			userShip = Optional.empty();
			return;
		}
		enemiesSet.remove(element);
	}

	@Override
	public void addAimableEnemy(Aimable enemy) {
		enemiesSet.add(enemy);
	}

	@Override
	public void setAimableUserShip(Aimable userShip) {
		this.userShip = Optional.ofNullable(userShip);
	}

}
