package my.projects.spacerangers2.game.concurrent;

public interface LevelSynchronizationManager {
	void pauseScene();
	void resumeScene();
	void disableScene();
	void enableScene();
	void sendAnimationTick();
}
