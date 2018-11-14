package my.projects.spacerangers2.game.entities;

import javafx.application.Platform;
import my.projects.spacerangers2.game.concurrent.LevelEntitySynchronizable;
import my.projects.spacerangers2.game.geometry.Point2D;
import my.projects.spacerangers2.game.objects.BoundableSpaceObject;

public abstract class ExplodableEntity<T extends BoundableSpaceObject<?>> extends SpaceEntity<T>{

	private Explosion explosion;
	
	public ExplodableEntity(LevelEntitySynchronizable synchronizer, T object) {
		super(synchronizer, object);
	}

	public void setExplosion(Explosion explosion) {
		this.explosion = explosion;
	}
	
	@Override
	protected void removeObjectFromScene() {
		if (explosion!=null) {
			Point2D centerPoint = object.getCenterPosition();
			explosion.object.setCenterPosition(centerPoint);
			Platform.runLater(()->{
				object.hide();
				explosion.initializeObject();
			});
		} else {
			super.removeObjectFromScene();
		}
		
	}
	
	protected void launchExplosionIfPresent() {
		if (explosion != null) {
			explosion.run();
		}
	}

}
