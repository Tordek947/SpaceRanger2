package my.projects.spacerangers2.game.concurrent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class GameModuleSynchronizer implements ModuleWarriorSynchronizable, ModuleSynchronizationManager{

	private AtomicInteger aliveEnemies;
	private AtomicBoolean shipIsAlive;
	
	public GameModuleSynchronizer() {
		aliveEnemies = new AtomicInteger(0);
		shipIsAlive = new AtomicBoolean(true);
	}
	
	public void incrementAliveEnemies() {
		aliveEnemies.incrementAndGet();
	}

	@Override
	public void waitForFightEnd() {
		synchronized (shipIsAlive) {
			try {
				shipIsAlive.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public boolean isShipAlive() {
		return shipIsAlive.get();
	}

	@Override
	public boolean enemyDyingIsLast() {
		if (aliveEnemies.decrementAndGet() == 0) {
			return true;
		}
		return false;
	}

	@Override
	public void userShipDying() {
		shipIsAlive.set(false);
	}

	@Override
	public void sendFightIsFinished() {
		synchronized (shipIsAlive) {
			shipIsAlive.notify();
		}
	}
}
