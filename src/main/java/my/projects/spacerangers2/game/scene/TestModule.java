package my.projects.spacerangers2.game.scene;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import my.projects.spacerangers2.game.entities.Asteroid;
import my.projects.spacerangers2.game.entities.SpaceEntityCreator;
import my.projects.spacerangers2.game.geometry.Vector2D;

public class TestModule extends GameModule {


	private Asteroid asteroids[];
	private int asteroidsCount;
	private Semaphore s;

	public TestModule(Vector2D sceneSize, SpaceEntityCreator entityCreator, Semaphore s) {
		super(sceneSize, entityCreator);
		this.s = s;
	}

	@Override
	protected void build() {
		asteroidsCount = 20;
		asteroids = new Asteroid[asteroidsCount];
		for(int i = 0;i<asteroidsCount;i++) {
			double left = Math.random()*(sceneSize.x-50);
			double right = Math.random()*(sceneSize.y-500);
			double speed = Math.random()*9 + 3;
			asteroids[i] = entityCreator.makeAsteroid(left, right, speed, 10, 25);
		}
	}

	@Override
	protected void execute() {
		for(int i = 0;i<asteroidsCount;i++) {
			Asteroid as = asteroids[i];
			Thread t = new Thread(()-> {
				as.run();
				s.release();
			});
			t.setDaemon(true);
			t.start();
			if (s.availablePermits() >= 1000) {
				break;
			}
			try {
				TimeUnit.MILLISECONDS.sleep((long)(Math.random()*200) + 400);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		try {
			s.acquire(asteroidsCount);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
