package my.projects.spacerangers2.game.entities;

import java.util.concurrent.Semaphore;

import my.projects.spacerangers2.game.common.Voice;
import my.projects.spacerangers2.game.concurrent.EntitySynchronizable;
import my.projects.spacerangers2.game.concurrent.WarriorSynchronizable;
import my.projects.spacerangers2.game.objects.AnimatedSpaceObject;

public class Explosion extends UnpausableSpaceEntity<AnimatedSpaceObject> {

	private Voice explosionVoice;
	private Semaphore voiceSemaphore;
	private boolean animationInProgress;
	private boolean isLastExplosion;
	
	public Explosion(WarriorSynchronizable synchronizer, AnimatedSpaceObject object, Voice explosionVoice) {
		super(synchronizer, object);
		this.explosionVoice = explosionVoice;
		voiceSemaphore = new Semaphore(0);
		explosionVoice.awakeThreadOnClipStop(voiceSemaphore);
		animationInProgress = true;
	}
	
	@Override
	protected void initializeObject() {
		explosionVoice.start();
		showObjectOnScene();
	}

	@Override
	protected boolean aliveCondition() {
		if (!object.isLastAnimationFrame()) {
			return animationInProgress;
		}
		animationInProgress = false;
		return true;
	}

	@Override
	protected void performLifecycleIteration() {
		object.nextAnimationFrame();
	}

	@Override
	protected void finalizeObject() {
		try {
			voiceSemaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		removeObjectFromScene();
		explosionVoice.close();
		if (isLastExplosion) {
			synchronizer.sendFightIsFinished();
		}
	}

	public void setLast(boolean isLastExplosion) {
		this.isLastExplosion = isLastExplosion;
	}

}
