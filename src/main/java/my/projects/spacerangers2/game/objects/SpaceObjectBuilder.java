package my.projects.spacerangers2.game.objects;

import javafx.scene.layout.Pane;
import my.projects.spacerangers2.game.common.Appearance;
import my.projects.spacerangers2.game.common.Appearances;
import my.projects.spacerangers2.game.common.SpriteAnimation;

public class SpaceObjectBuilder {
	private Pane representingStage;

	public SpaceObjectBuilder(Pane representingStage) {
		this.representingStage = representingStage;
	}
	
	public AnimatedSpaceObject makeAsteroidObject() {
		Appearance<SpriteAnimation> asteroidAppearance = Appearances.asteroidAppearance();
		return new AnimatedSpaceObject(asteroidAppearance, representingStage);
	}
}
