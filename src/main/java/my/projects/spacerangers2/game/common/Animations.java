package my.projects.spacerangers2.game.common;

import my.projects.resources.ImagesBuilder;

public class Animations {
	
	public static SpriteAnimation explosionSA() {
		ImagesBuilder ib = ImagesBuilder.getInstance();
		return new SpriteAnimation(ib.getExplosionImages(),0);
	}
	
	public static SpriteAnimation asteroidSA() {
		ImagesBuilder ib = ImagesBuilder.getInstance();
		return new SpriteAnimation(ib.getAsteroidImages());
	}
	
	public static SpriteAnimation userShipSA() {
		ImagesBuilder ib = ImagesBuilder.getInstance();
		return new SpriteAnimation(ib.getUserShipImages());
	}
	
	public static SingleImageAnimation bulletSIA() {
		ImagesBuilder ib = ImagesBuilder.getInstance();
		return new SingleImageAnimation(ib.getBulletImage());
	}

	public static SingleImageAnimation weaponAnimation() {
		ImagesBuilder ib = ImagesBuilder.getInstance();
		return new SingleImageAnimation(ib.getWeaponImage());
	}

}
