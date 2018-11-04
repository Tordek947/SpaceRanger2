package my.projects.spacerangers2.game.objects;

import javafx.scene.layout.Pane;
import my.projects.spacerangers2.game.common.Appearance;
import my.projects.spacerangers2.game.common.SingleImageAnimation;

public class SimpleSpaceObject extends SpaceObject<SingleImageAnimation> {

	public SimpleSpaceObject(Appearance<SingleImageAnimation> appearance, Pane representingStage) {
		super(appearance.getAnimation(), representingStage, appearance.getBounds());
		// TODO Auto-generated constructor stub
	}

}
