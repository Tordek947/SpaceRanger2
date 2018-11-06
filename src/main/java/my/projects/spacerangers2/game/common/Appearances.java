package my.projects.spacerangers2.game.common;

import my.projects.spacerangers2.game.geometry.Bounds;
import my.projects.spacerangers2.game.geometry.Circle;
import my.projects.spacerangers2.game.geometry.GeometricalPrimitive;
import my.projects.spacerangers2.game.geometry.Vector2D;

public class Appearances {
	
	public static Appearance<SpriteAnimation> userShipAppearance() {
		Bounds bounds = new Bounds();
		/*
		 * Here you have to initialize bounds
		 */
		return new Appearance<SpriteAnimation>(Animations.userShipSA(),bounds );
	}
	
	public static Appearance<SpriteAnimation> asteroidAppearance(){//
		Bounds bounds = new Bounds();
		GeometricalPrimitive circle = new Circle(18);
		Vector2D shiftToCircle = new Vector2D(24,24);
		bounds.addPrimitive(shiftToCircle, circle);
		return new Appearance<SpriteAnimation>(Animations.asteroidSA(), bounds);
	}
	
	public static Appearance<SingleImageAnimation> bulletAppearance(){
		Bounds bounds = new Bounds();
		/*
		 * Here you have to initialize bounds
		 */
		return new Appearance<SingleImageAnimation>(Animations.bulletSIA(),bounds );
	}
}
