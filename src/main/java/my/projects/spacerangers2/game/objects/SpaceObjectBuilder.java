package my.projects.spacerangers2.game.objects;

import javafx.scene.layout.Pane;
import my.projects.spacerangers2.game.common.Animations;
import my.projects.spacerangers2.game.common.Appearance;
import my.projects.spacerangers2.game.common.Appearances;
import my.projects.spacerangers2.game.common.SingleImageAnimation;
import my.projects.spacerangers2.game.common.SpriteAnimation;
import my.projects.spacerangers2.game.entities.Weapon;

public class SpaceObjectBuilder {
	
	private static SpaceObjectBuilder instance;

	private SpaceObjectBuilder() {}
	
	public static void initializeInstance(Pane representingStage) {
		if (representingStage != null) {
			instance = new SpaceObjectBuilder();
		}
	}
	
	public static SpaceObjectBuilder getInstance() {
		if (instance == null) {
			instance = new SpaceObjectBuilder();
		}
		return instance;
	}
	
	public AnimatedSpaceObject makeAsteroidObject() {
		Appearance<SpriteAnimation> asteroidAppearance = Appearances.asteroidAppearance();
		return new AnimatedSpaceObject(asteroidAppearance);
	}
	
	public AnimatedSpaceObject makeExplosionObject() {
		Appearance<SpriteAnimation> appearance = Appearances.explosionAppearance();
		return new AnimatedSpaceObject(appearance);
	}
	
	public SimpleSpaceObject makeBulletObject() {
		Appearance<SingleImageAnimation> appearance = Appearances.bulletAppearance();
		return new SimpleSpaceObject(appearance);
	}

	public AnimatedSpaceObject makeUserShipObject() {
		Appearance<SpriteAnimation> appearance = Appearances.userShipAppearance();
		return new AnimatedSpaceObject(appearance);
	}


	public Weapon makeWeapon() {
		SingleImageAnimation weaponAnimation = Animations.weaponAnimation();
		Weapon weapon = new Weapon(weaponAnimation);
		
		return weapon;
	}
}
