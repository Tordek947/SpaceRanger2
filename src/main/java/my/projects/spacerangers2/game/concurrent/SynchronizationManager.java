package my.projects.spacerangers2.game.concurrent;

public interface SynchronizationManager {
	void pauseScene();
	void resumeScene();
	void disableScene();
	void aquireAllDeadEnemyTokens();
}
