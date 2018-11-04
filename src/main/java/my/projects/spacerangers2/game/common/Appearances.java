package my.projects.spacerangers2.game.common;

import my.projects.spacerangers2.game.geometry.Bounds;

public class Appearances {
	
	public static Appearance<SpriteAnimation> userShipAppearance() {
		Bounds bounds = new Bounds();
		/*
		 * Here you have to initialize bounds
		 */
		return new Appearance<SpriteAnimation>(Animations.userShipSA(),bounds );
	}
	
	public static Appearance<SpriteAnimation> asteroidAppearance(){
		Bounds bounds = new Bounds();
		/*
		 * Here you have to initialize bounds
		 */
		return new Appearance<SpriteAnimation>(Animations.asteroidSA(),bounds );
	}
	
	public static Appearance<SingleImageAnimation> bulletAppearance(){
		Bounds bounds = new Bounds();
		/*
		 * Here you have to initialize bounds
		 */
		return new Appearance<SingleImageAnimation>(Animations.bulletSIA(),bounds );
	}
}
