package my.projects.spacerangers2.game.entities;

import javafx.scene.layout.Pane;
import my.projects.resources.MusicsBuilder;
import my.projects.resources.MusicsBuilder.StreamName;
import my.projects.spacerangers2.game.common.Voice;
import my.projects.spacerangers2.game.concurrent.AimableModifiableList;
import my.projects.spacerangers2.game.concurrent.LevelEntitySynchronizable;
import my.projects.spacerangers2.game.concurrent.GameLevelSynchronizer;
import my.projects.spacerangers2.game.geometry.Vector2D;
import my.projects.spacerangers2.game.objects.AnimatedSpaceObject;
import my.projects.spacerangers2.game.objects.SpaceObjectBuilder;

public class SpaceEntityCreator {
	private Vector2D sceneSize;
	private AimableModifiableList entitiesList;
	private SpaceObjectBuilder spaceObjectBuilder;
	private LevelEntitySynchronizable gameLevelSynchronizer;
	private Pane representingStage;
	
	public SpaceEntityCreator(Vector2D sceneSize, AimableModifiableList entitiesList,
			GameLevelSynchronizer gameLevelSynchronizer, Pane representingStage) {
		this.sceneSize = sceneSize;
		this.entitiesList = entitiesList;
		this.gameLevelSynchronizer = gameLevelSynchronizer;
		this.representingStage = representingStage;
		spaceObjectBuilder = SpaceObjectBuilder.getInstance();
		
	}
	
	public Asteroid makeAsteroid(double left, double top, double speed, double damage, int health) {
		AnimatedSpaceObject asteroidObject = spaceObjectBuilder.makeAsteroidObject();
		asteroidObject.setRepresentingStage(representingStage);
		Asteroid asteroid = new Asteroid(sceneSize, gameLevelSynchronizer, asteroidObject,
				entitiesList);
		asteroid.setDamage(damage);
		asteroid.setHealth(health);
		asteroid.setInitialPosition(left, top);
		asteroid.setSpeed(speed);
		asteroid.setExplosion(makeExplosion());
		return asteroid;
	}
	
	public Explosion makeExplosion() {
		AnimatedSpaceObject object = spaceObjectBuilder.makeExplosionObject();
		object.setRepresentingStage(representingStage);
		Voice explosionVoice = new Voice();
		explosionVoice.openClip(MusicsBuilder.getInstance().get(StreamName.EXPLOSION));
		Explosion explosion = new Explosion(gameLevelSynchronizer,
				object, explosionVoice);
		return explosion;
	}
	
	public UserShip makeUserShip(double speed, int health) {
		AnimatedSpaceObject shipObject = spaceObjectBuilder.makeUserShipObject();
		shipObject.setRepresentingStage(representingStage);
		
		UserShip userShip = new UserShip(gameLevelSynchronizer, shipObject, sceneSize,
				entitiesList);
		userShip.setExplosion(makeExplosion());
		userShip.setHealth(health);
		userShip.object.setXCenter(sceneSize.x/2);
		userShip.object.setBottom(sceneSize.y);
		userShip.setSpeed(speed);
		return userShip;
	}

	public Weapon makeWeapon(int shootPeriodInTicks,double bulletSpeed, double bulletDamage) {
		Weapon weapon = spaceObjectBuilder.makeWeapon();
		weapon.setRepresentingStage(representingStage);
		BulletSample bulletSample = makeDefaultBulletSample(bulletSpeed, bulletDamage);
		weapon.setShootingParameters(bulletSample, shootPeriodInTicks);
		return weapon;
	}

	private BulletSample makeDefaultBulletSample(double speed, double damage) {
		
		BulletSample bulletSample = new BulletSample(speed, damage, true, sceneSize,
				entitiesList, gameLevelSynchronizer, representingStage);
		return bulletSample;
	}

}
