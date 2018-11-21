package my.projects.spacerangers2.game.objects;

import my.projects.spacerangers2.game.common.Appearance;
import my.projects.spacerangers2.game.common.SingleImageAnimation;
import my.projects.spacerangers2.game.geometry.Point2D;

public class SimpleSpaceObject extends BoundableSpaceObject<SingleImageAnimation> {

	public SimpleSpaceObject(Appearance<SingleImageAnimation> appearance) {
		super(appearance.getAnimation(), appearance.getBounds(), new Point2D());
	}


}
