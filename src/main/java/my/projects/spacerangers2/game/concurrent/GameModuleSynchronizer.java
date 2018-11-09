package my.projects.spacerangers2.game.concurrent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class GameModuleSynchronizer implements WarriorSynchronizable, SynchronizationManager {

	private Object tickSynchronizer;
	private AtomicBoolean pause;
	private AtomicBoolean sceneIsDisabled;
	private AtomicInteger aliveEnemies;
	private AtomicBoolean shipIsAlive;
	
	public GameModuleSynchronizer() {
		tickSynchronizer = new Object();
		pause = new AtomicBoolean(true);
		sceneIsDisabled = new AtomicBoolean(true);
		aliveEnemies = new AtomicInteger(0);
		shipIsAlive = new AtomicBoolean(true);
	}
	
	public void incrementAliveEnemies() {
		aliveEnemies.incrementAndGet();
	}
	
	@Override
	public void pauseScene() {
		pause.set(true);
	}

	@Override
	public void resumeScene() {
		pause.set(false);
		synchronized(pause) {
			pause.notifyAll();
		}
	}

	@Override
	public void disableScene() {
		sceneIsDisabled.set(true);
	}

	@Override
	public void enableScene() {
		sceneIsDisabled.set(false);
	}

	@Override
	public void waitForSceneResume() {
		synchronized(pause) {
			try {
				pause.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void sendAnimationTick() {
		synchronized(tickSynchronizer) {
			tickSynchronizer.notifyAll();
		}
	}
	
	@Override
	public void waitForAnimationTick() {
		synchronized(tickSynchronizer) {
			try {
				tickSynchronizer.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean isScenePause() {
		return pause.get();
	}

	@Override
	public boolean isSceneDisabled() {
		return sceneIsDisabled.get();
	}

	@Override
	public boolean waitForFightEndAndGetIsShipAlive() {
		synchronized (shipIsAlive) {
			try {
				shipIsAlive.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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
