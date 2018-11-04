package my.projects.spacerangers2.game.common;

import my.projects.spacerangers2.game.geometry.Bounds;
import my.projects.spacerangers2.game.objects.BoundsInitializer;

public class Appearances {
	
	public static Appearance<SpriteAnimation> userShipAppearance() {
		BoundsInitializer userShipBoundsInitializer = new BoundsInitializer() {

			@Override
			public Bounds get() {
				Bounds bounds = new Bounds();
				/*
				 * Here write some code adding circles to the Bounds
				 */
				return bounds;
			}
			
		};
		return new Appearance<SpriteAnimation>(Animations.userShipSA(),userShipBoundsInitializer );
	}
	
	public static Appearance<SpriteAnimation> asteroidAppearance(){
		BoundsInitializer userShipBoundsInitializer = new BoundsInitializer() {

			@Override
			public Bounds get() {
				Bounds bounds = new Bounds();
				/*
				 * Here write some code adding circles to the Bounds
				 */
				return bounds;
			}
			
		};
		return new Appearance<SpriteAnimation>(Animations.asteroidSA(),userShipBoundsInitializer );
	}
	
	public static Appearance<SingleImageAnimation> bulletAppearance(){
		BoundsInitializer userShipBoundsInitializer = new BoundsInitializer() {

			@Override
			public Bounds get() {
				Bounds bounds = new Bounds();
				/*
				 * Here write some code adding circles to the Bounds
				 */
				return bounds;
			}
			
		};
		return new Appearance<SingleImageAnimation>(Animations.bulletSIA(),userShipBoundsInitializer );
	}
}
