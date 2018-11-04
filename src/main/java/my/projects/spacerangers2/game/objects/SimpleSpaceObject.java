package my.projects.spacerangers2.game.objects;

import javafx.scene.layout.Pane;
import my.projects.spacerangers2.game.common.Appearance;
import my.projects.spacerangers2.game.common.SingleImageAnimation;

public class SimpleSpaceObject extends SpaceObject<SingleImageAnimation> {

	private SimpleSpaceObject(Appearance<SingleImageAnimation> appearance, Pane representingStage) {
		super(appearance.getAnimation(), representingStage, appearance.getBoundsInitializer());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void perform() {
		// TODO Auto-generated method stub
		
	}

}
