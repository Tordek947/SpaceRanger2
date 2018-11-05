package my.projects.spacerangers2.game.entities;

import javafx.application.Platform;
import my.projects.spacerangers2.game.concurrent.EntitySynchronizable;
import my.projects.spacerangers2.game.objects.SpaceObject;

public abstract class SpaceEntity<T extends SpaceObject<?>> implements Runnable {
	
	private EntitySynchronizable synchronizer;
	protected T object;
	
	public SpaceEntity(EntitySynchronizable synchronizer, T object) {
		this.synchronizer = synchronizer;
		this.object = object;
	}
	
	public void setInitialPosition(double left, double top) {
		object.setLeftTopPosition(left, top);
	}
	
	@Override
	public void run() {
		initializeObject();
		while(aliveCondition()) {
			if (synchronizer.isScenePause()) {
				synchronizer.waitForSceneResume();
			}
			if (synchronizer.isSceneDisabled()) {
				break;
			}
			Platform.runLater(()->object.perform());
			computeNextState();
			synchronizer.waitForAnimationTick();
		}
		finalizeObject();
	}

	protected abstract void initializeObject();

	protected abstract boolean aliveCondition();

	protected abstract void computeNextState();

	protected abstract void finalizeObject();

	
}
