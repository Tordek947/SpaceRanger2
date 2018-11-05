package my.projects.spacerangers2.game.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameModuleSynchronizer implements EntitySynchronizable, SynchronizationManager {

	private Object tickSynchronizer;
	private AtomicBoolean pause;
	private AtomicBoolean sceneIsDisabled;
	private Semaphore gameModuleEntityWatcher;
	private int entityPermits;
	
	public GameModuleSynchronizer() {
		tickSynchronizer = new Object();
		pause = new AtomicBoolean(true);
		sceneIsDisabled = new AtomicBoolean(true);
		this.entityPermits = 0;
		gameModuleEntityWatcher = new Semaphore(0);
	}
	
	public void incrementTotalEntityPermits() {
		this.entityPermits++;
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
	public void aquireAllDeadEnemyTokens() {
		try {
			gameModuleEntityWatcher.acquire(entityPermits);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void releaseDeadEntityToken() {
		gameModuleEntityWatcher.release();
	}

	@Override
	public void releaseAllDeadEntityTokens() {
		gameModuleEntityWatcher.release(entityPermits);
	}


}
