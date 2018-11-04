package my.projects.spacerangers2.game.common;

import my.projects.spacerangers2.game.objects.BoundsInitializer;

public class Appearance<T extends AbstractAnimation> {
	private T animation;
	private BoundsInitializer boundsInitializer;
	
	protected Appearance(T animation, BoundsInitializer boundsInitializer) {
		this.animation = animation;
		this.boundsInitializer = boundsInitializer;
	}

	public T getAnimation() {
		return animation;
	}

	public BoundsInitializer getBoundsInitializer() {
		return boundsInitializer;
	}
		
}
