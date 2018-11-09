package my.projects.spacerangers2.game.scene;

import java.util.concurrent.TimeUnit;

import javafx.scene.layout.Pane;
import my.projects.spacerangers2.game.concurrent.AimableModifiableList;
import my.projects.spacerangers2.game.concurrent.SynchronizationManager;
import my.projects.spacerangers2.game.entities.Asteroid;
import my.projects.spacerangers2.game.geometry.Vector2D;

public class TestModule extends GameModule {

	private Asteroid asteroids[];

	public TestModule(Pane rootNode, Vector2D sceneSize, AimableModifiableList aimableList) {
		super(rootNode, sceneSize, aimableList);
	}

	@Override
	protected void buildLogic() {
		int n = 500;
		asteroids = new Asteroid[n];
		for(int i = 0;i<n;i++) {
			double left = Math.random()*(sceneSize.x-50);
			double right = Math.random()*(sceneSize.y-50);
			double speed = Math.random()*10 + 2;
			asteroids[i] = entityBuilder.makeAsteroid(left, right, speed, 50, 500);
		}
	}

	@Override
	protected void executeLogic() {
		SynchronizationManager manager = entityBuilder.getSynchronizationManager();
		
		for(Asteroid e : asteroids) {
			Thread t = new Thread(e);
			t.setDaemon(true);
			t.start();
			try {
				TimeUnit.MILLISECONDS.sleep((long)(Math.random()*200) + 800);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		
		boolean shipIsAlive = manager.waitForFightEndAndGetIsShipAlive();
	}

}
