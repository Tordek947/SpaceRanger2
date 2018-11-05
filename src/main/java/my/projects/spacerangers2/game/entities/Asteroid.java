package my.projects.spacerangers2.game.entities;

import java.util.concurrent.atomic.AtomicInteger;

import my.projects.spacerangers2.game.concurrent.EntitySynchronizable;
import my.projects.spacerangers2.game.geometry.Point2D;
import my.projects.spacerangers2.game.geometry.Vector2D;
import my.projects.spacerangers2.game.objects.AnimatedSpaceObject;

public class Asteroid extends SpaceEntity<AnimatedSpaceObject> {

	private AtomicInteger health;
	private double speed;
	private Vector2D velocity;
	private Vector2D sceneSize;
	private Explosion explosion;
	
	public Asteroid(Vector2D sceneSize, EntitySynchronizable synchronizer, AnimatedSpaceObject object, Explosion explosion) {
		super(synchronizer, object);
		health = new AtomicInteger();
		velocity = Vector2D.randomDirection();
		this.explosion = explosion;
	}

	public void setHealth(int health) {
		this.health.set(health);
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	@Override
	protected void initializeObject() {
		object.show();
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
	protected void computeNextState() {
		computeVelocity();
		health.decrementAndGet();
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
		object.hide();
		Point2D centerPoint = object.getCenterPosition();
		explosion.object.setCenterPosition(centerPoint);
		Thread t = new Thread(explosion);
		t.setDaemon(true);
		t.start();
	}

}
