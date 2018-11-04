package my.projects.spacerangers2.game.entities;

import javafx.application.Platform;
import my.projects.spacerangers2.game.concurrent.EntitySynchronizable;
import my.projects.spacerangers2.game.objects.SpaceObject;

public abstract class SpaceEntity<T extends SpaceObject<?>> implements Runnable{
	
	private EntitySynchronizable synchronizer;
	protected T object;
	
	public SpaceEntity(EntitySynchronizable synchronizer, T object) {
		this.synchronizer = synchronizer;
		this.object = object;
	}

	@Override
	public void run() {
		initializeObject();
		Platform.runLater(()->object.perform());
		while(aliveCondition()) {
			if (synchronizer.isStagePause()) {
				synchronizer.waitForStageResume();
			}
			computeNextState();
			Platform.runLater(()->object.perform());
			synchronizer.waitForAnimationTick();
		}
		finalizeObject();
	}

	protected abstract void initializeObject();

	protected abstract boolean aliveCondition();

	protected abstract void computeNextState();

	protected abstract void finalizeObject();
	
}
