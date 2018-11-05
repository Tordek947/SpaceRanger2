package my.projects.spacerangers2.game.entities;

import java.util.concurrent.Semaphore;

import my.projects.spacerangers2.game.common.Voice;
import my.projects.spacerangers2.game.concurrent.EntitySynchronizable;
import my.projects.spacerangers2.game.objects.AnimatedSpaceObject;

public class Explosion extends SpaceEntity<AnimatedSpaceObject> {

	private Voice explosionVoice;
	private Semaphore voiceSemaphore;
	private boolean animationInProgress;
	
	public Explosion(EntitySynchronizable synchronizer, AnimatedSpaceObject object, Voice explosionVoice) {
		super(synchronizer, object);
		this.explosionVoice = explosionVoice;
		voiceSemaphore = new Semaphore(0);
		explosionVoice.awakeThreadOnClipStop(voiceSemaphore);
		animationInProgress = true;
	}
	
	@Override
	protected void initializeObject() {
		object.show();
		explosionVoice.start();
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
	protected void computeNextState() {
		object.nextAnimationFrame();
	}

	@Override
	protected void finalizeObject() {
		try {
			voiceSemaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		object.hide();
		explosionVoice.close();
	}

}
