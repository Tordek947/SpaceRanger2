package my.projects.spacerangers2.game.entities;

import javafx.application.Platform;
import my.projects.spacerangers2.game.concurrent.EntitySynchronizable;
import my.projects.spacerangers2.game.objects.SpaceObject;

public abstract class UnpausableSpaceEntity<T extends SpaceObject<?>> implements Runnable {
	
	private EntitySynchronizable synchronizer;
	protected T object;
	
	public UnpausableSpaceEntity(EntitySynchronizable synchronizer, T object) {
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
			if (synchronizer.isSceneDisabled()) {
				break;
			}
			Platform.runLater(()->object.perform());
			performLifecycleIteration();
			synchronizer.waitForAnimationTick();
		}
		finalizeObject();
	}

	protected abstract void initializeObject();

	protected abstract boolean aliveCondition();

	protected abstract void performLifecycleIteration();

	protected abstract void finalizeObject();

}
