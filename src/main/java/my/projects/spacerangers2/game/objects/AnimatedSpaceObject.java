package my.projects.spacerangers2.game.objects;

import my.projects.spacerangers2.game.common.Appearance;
import my.projects.spacerangers2.game.common.SpriteAnimation;
import my.projects.spacerangers2.game.geometry.Point2D;

public class AnimatedSpaceObject extends BoundableSpaceObject<SpriteAnimation>{

	public AnimatedSpaceObject(Appearance<SpriteAnimation> appearance) {
		super(appearance.getAnimation(), appearance.getBounds(), new Point2D());
	}

	public void nextAnimationFrame() {
		animation.flipForward();
	}
	
	public void previousAnimationFrame() {
		animation.flipBack();
	}
	
	public void flipToCentralFrame() {
		animation.flipToCenter();
	}

	public boolean isFirstAnimationFrame() {
		return animation.isFirstFlip();
	}

	public boolean isLastAnimationFrame() {
		return animation.isLastFlip();
	}

	public boolean isCentralAnimationFrame() {
		return animation.isCentralFlip();
	}
}
