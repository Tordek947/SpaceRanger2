package my.projects.spacerangers2.game.entities;

import my.projects.spacerangers2.game.concurrent.LevelEntitySynchronizable;
import my.projects.spacerangers2.game.geometry.Point2D;
import my.projects.spacerangers2.game.objects.SpaceObject;

public abstract class ExplodableEntity<T extends SpaceObject<?>> extends SpaceEntity<T>{

	private Explosion explosion;
	
	public ExplodableEntity(LevelEntitySynchronizable synchronizer, T object) {
		super(synchronizer, object);
	}

	public void setExplosion(Explosion explosion) {
		this.explosion = explosion;
	}
	
	protected void launchExplosionIfPresent(boolean lastExplosion) {
		if (explosion != null) {
			Point2D centerPoint = object.getCenterPosition();
			explosion.object.setCenterPosition(centerPoint);
			explosion.setLast(lastExplosion);
			Thread t = new Thread(explosion);
			t.setDaemon(true);
			t.start();
		}
	}
	
	protected void launchExplosionIfPresent() {
		launchExplosionIfPresent(false);
	}
}
