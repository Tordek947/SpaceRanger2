package my.projects.spacerangers2.game.entities;

import my.projects.spacerangers2.game.concurrent.GameModuleSynchronizer;
import my.projects.spacerangers2.game.concurrent.SynchronizationManager;

public class SpaceEntityBuilder {
	private GameModuleSynchronizer gameModuleSynchronizer;
	
	public SpaceEntityBuilder() {
		gameModuleSynchronizer = new GameModuleSynchronizer();
	}
	
	
	public SynchronizationManager setEntityPermitsAndGetSynchronizationManager(int entityPermits) {
		gameModuleSynchronizer.setEntityPermits(entityPermits);
		return gameModuleSynchronizer;
	}
}
