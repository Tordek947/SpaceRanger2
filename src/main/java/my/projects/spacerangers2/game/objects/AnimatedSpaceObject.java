package my.projects.spacerangers2.game.objects;

import javafx.scene.layout.Pane;
import my.projects.spacerangers2.game.common.Appearance;
import my.projects.spacerangers2.game.common.SpriteAnimation;

public class AnimatedSpaceObject extends SpaceObject<SpriteAnimation>{

	public AnimatedSpaceObject(Appearance<SpriteAnimation> appearance, Pane representingStage) {
		super(appearance.getAnimation(), representingStage, appearance.getBounds());
		// TODO Auto-generated constructor stub
	}

	public void nextAnimationFrame() {
		animation.flipForward();
	}
	
	public void previousAnimationFrame() {
		animation.flipBack();
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
