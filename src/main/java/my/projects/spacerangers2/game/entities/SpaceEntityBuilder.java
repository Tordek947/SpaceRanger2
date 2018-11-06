package my.projects.spacerangers2.game.entities;

import my.projects.spacerangers2.game.concurrent.AimableList;
import my.projects.spacerangers2.game.concurrent.AimableWatchList;
import my.projects.spacerangers2.game.concurrent.EntitySynchronizable;
import my.projects.spacerangers2.game.concurrent.GameModuleSynchronizer;
import my.projects.spacerangers2.game.concurrent.SynchronizationManager;
import my.projects.spacerangers2.game.geometry.Vector2D;
import my.projects.spacerangers2.game.objects.AnimatedSpaceObject;
import my.projects.spacerangers2.game.objects.SpaceObjectBuilder;

public class SpaceEntityBuilder {
	private GameModuleSynchronizer gameModuleSynchronizer;
	private Vector2D sceneSize;
	private AimableList entitiesList;
	private SpaceObjectBuilder spaceObjectBuilder;
	
	public SpaceEntityBuilder(Vector2D sceneSize, AimableList entitiesList, SpaceObjectBuilder spaceObjectBuilder) {
		gameModuleSynchronizer = new GameModuleSynchronizer();
		this.sceneSize = sceneSize;
		this.entitiesList = entitiesList;
		this.spaceObjectBuilder = spaceObjectBuilder;
	}
	
	public SynchronizationManager getSynchronizationManager() {
		return gameModuleSynchronizer;
	}
	
	public Asteroid makeAsteroid(double left, double top, double speed, double damage, int health) {
		AnimatedSpaceObject asteroidObject = spaceObjectBuilder.makeAsteroidObject();
		Asteroid asteroid = new Asteroid(sceneSize, gameModuleSynchronizer, asteroidObject, entitiesList);
		asteroid.setDamage(damage);
		asteroid.setHealth(health);
		asteroid.setInitialPosition(left, top);
		asteroid.setSpeed(speed);
		//asteroid.setExplosion(new Explosion());
		return asteroid;
	}
}
