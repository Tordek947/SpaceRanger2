package my.projects.spacerangers2.game.concurrent;

public interface SynchronizationManager {
	void pauseScene();
	void resumeScene();
	void disableScene();
	void enableScene();
	void sendAnimationTick();
	boolean waitForModuleEndAndGetIsShipAlive();
}
