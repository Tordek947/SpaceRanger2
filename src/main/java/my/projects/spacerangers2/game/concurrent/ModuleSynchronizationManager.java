package my.projects.spacerangers2.game.concurrent;

public interface ModuleSynchronizationManager {
	void waitForFightEnd();
	boolean isShipAlive();
}
