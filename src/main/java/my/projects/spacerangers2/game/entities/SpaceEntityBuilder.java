package my.projects.spacerangers2.game.entities;

import my.projects.resources.MusicsBuilder;
import my.projects.resources.MusicsBuilder.StreamName;
import my.projects.spacerangers2.game.common.Voice;
import my.projects.spacerangers2.game.concurrent.AimableModifiableList;
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
	private AimableModifiableList entitiesList;
	private SpaceObjectBuilder spaceObjectBuilder;
	
	public SpaceEntityBuilder(Vector2D sceneSize, AimableModifiableList entitiesList, SpaceObjectBuilder spaceObjectBuilder) {
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
		asteroid.setExplosion(makeExplosion());
		entitiesList.addAimableEnemy(asteroid);
		gameModuleSynchronizer.incrementAliveEnemies();
		return asteroid;
	}
	
	public Explosion makeExplosion() {
		AnimatedSpaceObject object = spaceObjectBuilder.makeExplosionObject();
		Voice explosionVoice = new Voice();
		explosionVoice.openClip(MusicsBuilder.getInstance().get(StreamName.EXPLOSION));
		Explosion explosion = new Explosion(gameModuleSynchronizer, object, explosionVoice);
		return explosion;
	}
}
