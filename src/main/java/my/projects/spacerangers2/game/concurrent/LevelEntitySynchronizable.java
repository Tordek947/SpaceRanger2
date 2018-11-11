package my.projects.spacerangers2.game.concurrent;

public interface LevelEntitySynchronizable{
	void waitForSceneResume();
	void waitForAnimationTick();
	boolean isScenePause();
	boolean isSceneDisabled();
}
