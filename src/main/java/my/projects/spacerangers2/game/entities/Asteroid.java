package my.projects.spacerangers2.game.entities;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import javafx.application.Platform;
import my.projects.spacerangers2.game.concurrent.AimableWatchList;
import my.projects.spacerangers2.game.concurrent.EntitySynchronizable;
import my.projects.spacerangers2.game.geometry.Point2D;
import my.projects.spacerangers2.game.geometry.Vector2D;
import my.projects.spacerangers2.game.objects.AnimatedSpaceObject;

public class Asteroid extends SpaceEntity<AnimatedSpaceObject>{

	private AtomicInteger health;
	private double speed;
	private double damage;
	private Vector2D velocity;
	private Vector2D sceneSize;
	private Explosion explosion;
	private AimableWatchList aimableWatchList;
	
	public Asteroid(Vector2D sceneSize, EntitySynchronizable synchronizer, AnimatedSpaceObject object,
			AimableWatchList aimableWatchList) {
		super(synchronizer, object);
		health = new AtomicInteger();
		velocity = Vector2D.randomDirection();
		this.sceneSize = sceneSize;
		this.aimableWatchList = aimableWatchList;
	}

	public void setHealth(int health) {
		this.health.set(health);
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public void setDamage(double damage) {
		this.damage = damage;
	}
	
	public void setExplosion(Explosion explosion) {
		this.explosion = explosion;
	}
	
	@Override
	protected void initializeObject() {
		showObjectOnScene();
		velocity = Vector2D.scale(velocity, speed);
	}

	@Override
	protected boolean aliveCondition() {
		if (health.get() > 0) {
			return true;
		}
		return false;
	}

	@Override
	protected void performLifecycleIteration() {
		Optional<Aimable> userShipBounds = aimableWatchList.getUserShip();
		userShipBounds.ifPresent(new IfIntersectsDamagePerformer());
		computeNextState();
	}

	private void computeNextState() {
		computeVelocity();
		health.decrementAndGet();//
		object.moveByVector(velocity);
		object.nextAnimationFrame();
	}

	private void computeVelocity() {
		javafx.geometry.Bounds bounds = object.getApproximateBounds();
		if (bounds.getMinX() < 0) {
			velocity.x = Math.abs(velocity.x);
		} else if (bounds.getMaxX() > sceneSize.x) {
			velocity.x = -Math.abs(velocity.x);
		}
		if (bounds.getMinY() < 0) {
			velocity.y = Math.abs(velocity.y);
		} else if (bounds.getMaxY() > sceneSize.y) {
			velocity.y = -Math.abs(velocity.y);
		}
	}

	@Override
	protected void finalizeObject() {
		aimableWatchList.remove(this);
		removeObjectFromScene();
		synchronizer.sendEnemyIsDead();
		if (explosion != null) {
			launchExplosion();
		}
	}

	private void launchExplosion() {
		Point2D centerPoint = object.getCenterPosition();
		explosion.object.setCenterPosition(centerPoint);
		Thread t = new Thread(explosion);
		t.setDaemon(true);
		t.start();
	}

	@Override
	public void recieveDamage(double damage) {
		health.getAndAdd(-(int)damage);
	}
	
	private class IfIntersectsDamagePerformer implements Consumer<Aimable> {

		@Override
		public void accept(Aimable t) {
			if (t.getApproximateBounds().intersects(object.getApproximateBounds()) && 
					t.getBounds().intersect(object.getBounds())) {
				t.recieveDamage(damage);
			}
		}
		
	}
}
