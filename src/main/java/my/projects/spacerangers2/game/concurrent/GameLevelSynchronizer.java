package my.projects.spacerangers2.game.concurrent;

import java.util.concurrent.atomic.AtomicBoolean;

public class GameLevelSynchronizer implements LevelEntitySynchronizable, LevelSynchronizationManager {

	private Object tickSynchronizer;
	private AtomicBoolean pause;
	private AtomicBoolean sceneIsDisabled;
	
	public GameLevelSynchronizer() {
		tickSynchronizer = new Object();
		pause = new AtomicBoolean(true);
		sceneIsDisabled = new AtomicBoolean(true);
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

}
