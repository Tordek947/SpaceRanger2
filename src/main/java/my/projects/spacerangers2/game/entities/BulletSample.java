package my.projects.spacerangers2.game.entities;


import javafx.scene.layout.Pane;
import my.projects.spacerangers2.game.concurrent.AimableWatchList;
import my.projects.spacerangers2.game.concurrent.LevelEntitySynchronizable;
import my.projects.spacerangers2.game.geometry.Vector2D;
import my.projects.spacerangers2.game.objects.SimpleSpaceObject;
import my.projects.spacerangers2.game.objects.SpaceObjectBuilder;

public class BulletSample {
	private double speed;
	private double damage;
	private boolean upDirection;
	private Vector2D sceneSize;
	private AimableWatchList aimableList;
	private LevelEntitySynchronizable synchronizer;
	private Pane representingStage;
	
	public static Bullet makeBulletFrom(BulletSample sample) {
		SimpleSpaceObject object = null;
		object = SpaceObjectBuilder.getInstance().makeBulletObject();
		object.setRepresentingStage(sample.representingStage);
		Bullet copy = new Bullet(sample.sceneSize, sample.synchronizer, object,
				sample.aimableList);
		copy.setDamage(sample.damage);
		copy.setSpeed(sample.speed);
		copy.setSpeedDirection(sample.upDirection);
		return copy;
	}

	public BulletSample(double speed, double damage, boolean upDirection, Vector2D sceneSize,
			AimableWatchList aimableList, LevelEntitySynchronizable synchronizer, Pane representingStage) {
		this.speed = speed;
		this.damage = damage;
		this.upDirection = upDirection;
		this.sceneSize = sceneSize;
		this.aimableList = aimableList;
		this.synchronizer = synchronizer;
		this.representingStage = representingStage;
	}
	
	
}
