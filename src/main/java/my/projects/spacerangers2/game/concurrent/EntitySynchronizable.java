package my.projects.spacerangers2.game.concurrent;

public interface EntitySynchronizable{
	void waitForSceneResume();
	void waitForAnimationTick();
	boolean isScenePause();
	boolean isSceneDisabled();
	void sendEnemyIsDead();
	void sendUserShipIsDead();
}
