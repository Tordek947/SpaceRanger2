package my.projects.spacerangers2.game.entities;

import java.util.concurrent.Semaphore;

import my.projects.spacerangers2.game.common.Voice;
import my.projects.spacerangers2.game.concurrent.LevelEntitySynchronizable;
import my.projects.spacerangers2.game.concurrent.ModuleWarriorSynchronizable;
import my.projects.spacerangers2.game.objects.AnimatedSpaceObject;

public class Explosion extends UnpausableSpaceEntity<AnimatedSpaceObject> {

	private Voice explosionVoice;
	private Semaphore voiceSemaphore;
	private ModuleWarriorSynchronizable warriorSynchronizer;
	private boolean animationInProgress;
	private boolean isLastExplosion;
	
	public Explosion(ModuleWarriorSynchronizable warriorSynchronizer, LevelEntitySynchronizable synchronizer,
			AnimatedSpaceObject object, Voice explosionVoice) {
		super(synchronizer, object);
		this.warriorSynchronizer = warriorSynchronizer;
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
			warriorSynchronizer.sendFightIsFinished();
		}
	}

	public void setLast(boolean isLastExplosion) {
		this.isLastExplosion = isLastExplosion;
	}

}
