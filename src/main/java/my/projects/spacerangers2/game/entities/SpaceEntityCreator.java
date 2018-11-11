package my.projects.spacerangers2.game.entities;

import my.projects.resources.MusicsBuilder;
import my.projects.resources.MusicsBuilder.StreamName;
import my.projects.spacerangers2.game.common.Voice;
import my.projects.spacerangers2.game.concurrent.AimableModifiableList;
import my.projects.spacerangers2.game.concurrent.AimableWatchList;
import my.projects.spacerangers2.game.concurrent.LevelEntitySynchronizable;
import my.projects.spacerangers2.game.concurrent.GameLevelSynchronizer;
import my.projects.spacerangers2.game.concurrent.GameModuleSynchronizer;
import my.projects.spacerangers2.game.concurrent.LevelSynchronizationManager;
import my.projects.spacerangers2.game.concurrent.ModuleSynchronizationManager;
import my.projects.spacerangers2.game.concurrent.ModuleWarriorSynchronizable;
import my.projects.spacerangers2.game.geometry.Vector2D;
import my.projects.spacerangers2.game.objects.AnimatedSpaceObject;
import my.projects.spacerangers2.game.objects.SpaceObjectBuilder;

public class SpaceEntityCreator {
	private GameLevelSynchronizer gameLevelSynchronizer;
	private GameModuleSynchronizer gameModuleSynchronizer;
	private Vector2D sceneSize;
	private AimableModifiableList entitiesList;
	private SpaceObjectBuilder spaceObjectBuilder;
	
	public SpaceEntityCreator(Vector2D sceneSize, AimableModifiableList entitiesList, SpaceObjectBuilder spaceObjectBuilder,
			GameLevelSynchronizer gameLevelSynchronizer) {
		this.gameLevelSynchronizer = gameLevelSynchronizer;
		gameModuleSynchronizer = new GameModuleSynchronizer();
		this.sceneSize = sceneSize;
		this.entitiesList = entitiesList;
		this.spaceObjectBuilder = spaceObjectBuilder;
	}
	
	public ModuleSynchronizationManager getModuleSynchronizationManager() {
		return gameModuleSynchronizer;
	}
	
	public Asteroid makeAsteroid(double left, double top, double speed, double damage, int health) {
		AnimatedSpaceObject asteroidObject = spaceObjectBuilder.makeAsteroidObject();
		Asteroid asteroid = new Asteroid(sceneSize, gameLevelSynchronizer, asteroidObject,
				entitiesList, gameModuleSynchronizer);
		asteroid.setDamage(damage);
		asteroid.setHealth(health);
		asteroid.setInitialPosition(left, top);
		asteroid.setSpeed(speed);
		asteroid.setExplosion(makeExplosion());
		gameModuleSynchronizer.incrementAliveEnemies();
		return asteroid;
	}
	
	public Explosion makeExplosion() {
		AnimatedSpaceObject object = spaceObjectBuilder.makeExplosionObject();
		Voice explosionVoice = new Voice();
		explosionVoice.openClip(MusicsBuilder.getInstance().get(StreamName.EXPLOSION));
		Explosion explosion = new Explosion(gameModuleSynchronizer, gameLevelSynchronizer,
				object, explosionVoice);
		return explosion;
	}
}
