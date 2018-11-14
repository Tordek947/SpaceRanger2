package my.projects.spacerangers2.game.entities;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import my.projects.spacerangers2.game.concurrent.AimableModifiableList;
import my.projects.spacerangers2.game.concurrent.LevelEntitySynchronizable;
import my.projects.spacerangers2.game.geometry.Vector2D;
import my.projects.spacerangers2.game.objects.AnimatedSpaceObject;

public class UserShip extends ExplodableEntity<AnimatedSpaceObject> implements Aimable, Weaponarizable {

	private Weapon weapon;
	private Optional<Weapon> weaponOptional;
	private Vector2D sceneSize;
	private AimableModifiableList aimableList;
	private AtomicInteger health;
	private double speed;
	private ShipDirection direction;
	private Vector2D velocity;
	
	
	public UserShip(LevelEntitySynchronizable synchronizer, AnimatedSpaceObject object, Vector2D sceneSize,
			AimableModifiableList aimableList) {
		super(synchronizer, object);
		this.sceneSize = sceneSize;
		this.aimableList = aimableList;
		health = new AtomicInteger();
	}
	
	public void setHealth(int health) {
		this.health.set(health);
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	@Override
	public void recieveDamage(double damage) {
		health.addAndGet((int) -damage);
	}


	@Override
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
		weapon.setBottom(object.getTop());
		weapon.setXCenter(object.getLeftRightCenter());
		weaponOptional = Optional.of(weapon);
	}

	

	@Override
	protected void initializeObject() {
		showObjectOnScene();
		
		aimableList.setAimableUserShip(this);
		direction = ShipDirection.NONE;
		velocity = new Vector2D(0,0);
	}

	@Override
	protected void showObjectOnScene() {
		if (weapon != null) {
			Platform.runLater(()->{
				object.show();
				weapon.show();
			});
		} else {
			super.showObjectOnScene();
		}
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
		if (weapon != null) {
			weapon.tickAndMaybeShoot();
		}
		computeNextState();
		weaponOptional = Optional.of(weapon);
	}


	private void computeNextState() {
		checkSceneLimit();
		calculateVelocity();
		object.moveByVector(velocity);
		if (weapon != null) {
			weapon.moveByVector(velocity);
		}
		defineAnimationState();
		
	}


	private void checkSceneLimit() {
		javafx.geometry.Bounds bounds = object.getApproximateBounds();
		if ((bounds.getMinX() <= 0 && direction == ShipDirection.LEFT)||
				(bounds.getMaxX() >= sceneSize.x && direction == ShipDirection.RIGHT)) {
			setShipDirection(ShipDirection.NONE);
		}
	}

	private void setShipDirection(ShipDirection newDirection) {
		direction = newDirection;
	}

	private void calculateVelocity() {
		switch(direction) {
		case LEFT:
			velocity.x = -1*speed;
			break;
		case NONE:
			velocity.x = 0;
			break;
		case RIGHT:
			velocity.x = 1*speed;
			break;
		default:
			break;
		}
		velocity.y = 0;
	}
	
	private void defineAnimationState() {
		switch(direction) {
		case LEFT:
			if (!object.isFirstAnimationFrame()) {
				object.previousAnimationFrame();
			}
			break;
		case NONE:
			if (!object.isCentralAnimationFrame()) {
				object.flipToCentralFrame();
			}
			break;
		case RIGHT:
			if (!object.isLastAnimationFrame()) {
				object.nextAnimationFrame();
			}
			break;
		default:
			break;
		
		}
	}
	@Override
	protected void perform() {
		object.perform();
		weaponOptional.ifPresent(Weapon::perform);
	}

	@Override
	protected void finalizeObject() {
		aimableList.remove(this);
		removeObjectFromScene();
		launchExplosionIfPresent();
	}
	
	@Override
	protected void removeObjectFromScene() {
		if (weapon != null) {
			Platform.runLater(()->{
				weapon.hide();
			});
		}
		super.removeObjectFromScene();
	}


	public boolean isDead() {
		if(health.get() <= 0) {
			return true;
		}
		return false;
	}

	public boolean isAlive() {
		if (health.get() > 0) {
			return true;
		}
		return false;
	}
	
	public EventHandler<KeyEvent> getKeyEventHandler() {
		return new EventHandler<KeyEvent>() {
			final KeyCombination left = new KeyCodeCombination(KeyCode.LEFT);
	    	final KeyCombination right = new KeyCodeCombination(KeyCode.RIGHT);
	    	final KeyCombination down = new KeyCodeCombination(KeyCode.DOWN);
			@Override
			public void handle(KeyEvent event) {
				if (left.match(event)) {
					setShipDirection(ShipDirection.LEFT);
				} else if (right.match(event)) {
					setShipDirection(ShipDirection.RIGHT);
				} else if (down.match(event)) {
					setShipDirection(ShipDirection.NONE);
				}
			}
			
		};
	}

}
