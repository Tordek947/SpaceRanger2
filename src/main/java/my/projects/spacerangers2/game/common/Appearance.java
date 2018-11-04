package my.projects.spacerangers2.game.common;

import my.projects.spacerangers2.game.geometry.Bounds;

public class Appearance<T extends AbstractAnimation> {
	private T animation;
	private Bounds bounds;
	
	protected Appearance(T animation, Bounds bounds) {
		this.animation = animation;
		this.bounds = bounds;
	}

	public T getAnimation() {
		return animation;
	}

	public Bounds getBounds() {
		return bounds;
	}
		
}
