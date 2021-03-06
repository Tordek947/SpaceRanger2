package my.projects.spacerangers2.game.entities;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import my.projects.spacerangers2.game.concurrent.AimableModifiableList;
import my.projects.spacerangers2.game.concurrent.LevelEntitySynchronizable;
import my.projects.spacerangers2.game.geometry.Point2D;
import my.projects.spacerangers2.game.geometry.Vector2D;
import my.projects.spacerangers2.game.objects.AnimatedSpaceObject;

public class Asteroid extends ExplodableEntity<AnimatedSpaceObject> implements Aimable{
	
	public static AtomicInteger deathsCount = new AtomicInteger(0);
	private AtomicInteger health;
	private double speed;
	private double damage;
	private Vector2D velocity;
	private Vector2D sceneSize;
	private AimableModifiableList aimableList;
	private Consumer<Aimable> ifIntersectsHitPerformer;
	
	public Asteroid(Vector2D sceneSize,LevelEntitySynchronizable synchronizer , AnimatedSpaceObject object,
			AimableModifiableList aimableList) {
		super(synchronizer, object);
		health = new AtomicInteger();
		velocity = Vector2D.randomDirection();
		this.sceneSize = sceneSize;
		this.aimableList = aimableList;
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
	
	@Override
	protected void initializeObject() {
		showObjectOnScene();
		aimableList.addAimableEnemy(this);
		ifIntersectsHitPerformer = new IfIntersectsHitPerformer();
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
		Optional<Aimable> userShipBounds = aimableList.getUserShip();
		userShipBounds.ifPresent(ifIntersectsHitPerformer);
		computeNextState();
		//System.out.println("is living..."+health.get()+" "+this.object.getLeft()+" "+this.object.getTop());
	}

	protected void computeNextState() {
		computeVelocity();
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
		if (velocity.x == Double.NaN || velocity.y == Double.NaN) {
			System.out.println("some velocity is NaN!!");
		}
	}

	@Override
	protected void finalizeObject() {
		aimableList.remove(this);
		removeObjectFromScene();
		launchExplosionIfPresent();
	}


	@Override
	public void recieveDamage(double damage) {
		health.getAndAdd(-(int)damage);
	}
	
	private class IfIntersectsHitPerformer implements Consumer<Aimable> {

		@Override
		public void accept(Aimable t) {
			if (t.getApproximateBounds().intersects(object.getApproximateBounds()) &&
					t.getBounds().intersect(object.getBounds())) {
				t.recieveDamage(damage);
				recieveDamage(damage);
				translateVelocityAwayFromEnemy(t);
			}
		}
		
	}

	private void translateVelocityAwayFromEnemy(Aimable t) {
		Point2D tCenter = makeCentralPointFromAimable(t);
		Point2D thisCenter = makeCentralPointFromAimable(this);
		velocity = Vector2D.scale(Vector2D.ortFromPoints(tCenter, thisCenter), speed);
	}

	private Point2D makeCentralPointFromAimable(Aimable t) {
		javafx.geometry.Bounds b = t.getApproximateBounds();
		double x = b.getMinX() + b.getWidth()/2;
		double y = b.getMinY() + b.getHeight()/2;
		return new Point2D(x,y);
	}
}
