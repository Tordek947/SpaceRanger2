package my.projects.spacerangers2.game.entities;

//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.Serializable;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;

import my.projects.spacerangers2.game.concurrent.AimableWatchList;
import my.projects.spacerangers2.game.concurrent.LevelEntitySynchronizable;
import my.projects.spacerangers2.game.geometry.Vector2D;
import my.projects.spacerangers2.game.objects.SimpleSpaceObject;

public class Bullet extends ExplodableEntity<SimpleSpaceObject> {
	
	private double speed;
	private double damage;
	private boolean isAlive;
	private boolean targetIsUserShip;
	private Vector2D velocity;
	private Vector2D sceneSize;
	private AimableWatchList aimableList;
	private Consumer<Aimable> ifIntersectsHitPerformer;
	
	public Bullet(Vector2D sceneSize, LevelEntitySynchronizable synchronizer, SimpleSpaceObject object,
			AimableWatchList aimableList) {
		super(synchronizer, object);
		this.sceneSize = sceneSize;
		this.aimableList = aimableList;
		isAlive = true;
		targetIsUserShip = false;
	}
//	
//	public static Bullet copyOf(Bullet other) {
//		Bullet copyOfOther = null;
//		ByteArrayOutputStream baos = new ByteArrayOutputStream(64);
//		ObjectOutputStream ous;
//		try {
//			ous = new ObjectOutputStream(baos);
//			ous.writeObject(other);
//			ous.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
//		ObjectInputStream ois;
//		try {
//			ois = new ObjectInputStream(bais);
//			copyOfOther = (Bullet)ois.readObject();
//		} catch (ClassNotFoundException | IOException e) {
//			e.printStackTrace();
//		}
//		return copyOfOther;
//		
//	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	public void setSpeedDirection(boolean up) {
		velocity = new Vector2D(0,up? -1 : 1);
	}
	
	@Override
	protected void initializeObject() {
		showObjectOnScene();
		ifIntersectsHitPerformer = new IfIntersectsHitPerformer();
		velocity = Vector2D.scale(velocity, speed);
	}

	@Override
	protected boolean aliveCondition() {
		return isAlive;
	}

	@Override
	protected void performLifecycleIteration() {
		if (targetIsUserShip) {
			Optional<Aimable> userShipBounds = aimableList.getUserShip();
			userShipBounds.ifPresent(ifIntersectsHitPerformer);
		} else {
			Iterator<Aimable> enemies = aimableList.observeEnemies();
			enemies.forEachRemaining(ifIntersectsHitPerformer);//
		}
		computeNextState();
	}

	private void computeNextState() {
		object.moveByVector(velocity);
		javafx.geometry.Bounds bounds = object.getApproximateBounds();
		if (bounds.getMinY() > sceneSize.y || bounds.getMaxY() < 0) {
			isAlive = false;
		}
	}

	@Override
	protected void finalizeObject() {
		removeObjectFromScene();
		launchExplosionIfPresent();
	}
	
	private class IfIntersectsHitPerformer implements Consumer<Aimable> {
		
		@Override
		public void accept(Aimable t) {
			if (isAlive && t.getApproximateBounds().intersects(object.getApproximateBounds()) && 
					t.getBounds().intersect(object.getBounds())) {
				t.recieveDamage(damage);
				isAlive = false;
			}
		}
		
	}

}
