package my.projects.spacerangers2.game.concurrent;

public interface EntitySynchronizable{
	void waitForSceneResume();
	void waitForAnimationTick();
	boolean isScenePause();
	boolean isSceneDisabled();
	void releaseDeadEntityToken();
	void releaseAllDeadEntityTokens();
}
