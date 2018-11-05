package my.projects.spacerangers2.game.entities;

import my.projects.spacerangers2.game.concurrent.GameModuleSynchronizer;
import my.projects.spacerangers2.game.concurrent.SynchronizationManager;
import my.projects.spacerangers2.game.geometry.Vector2D;

public class SpaceEntityBuilder {
	private GameModuleSynchronizer gameModuleSynchronizer;
	private Vector2D sceneSize;
	
	public SpaceEntityBuilder(Vector2D sceneSize) {
		gameModuleSynchronizer = new GameModuleSynchronizer();
		this.sceneSize = sceneSize;
	}
	
	
	public SynchronizationManager getSynchronizationManager() {
		return gameModuleSynchronizer;
	}
}
