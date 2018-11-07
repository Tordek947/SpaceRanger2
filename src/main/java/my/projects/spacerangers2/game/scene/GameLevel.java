package my.projects.spacerangers2.game.scene;

import java.util.concurrent.TimeUnit;

import javafx.animation.AnimationTimer;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import my.projects.resources.MusicsBuilder;
import my.projects.resources.MusicsBuilder.StreamName;
import my.projects.spacerangers2.game.common.Voice;
import my.projects.spacerangers2.game.concurrent.AimableModifiableList;
import my.projects.spacerangers2.game.concurrent.AimableSkipList;
import my.projects.spacerangers2.game.concurrent.SynchronizationManager;
import my.projects.spacerangers2.game.entities.Asteroid;
import my.projects.spacerangers2.game.entities.SpaceEntityBuilder;
import my.projects.spacerangers2.game.geometry.Vector2D;
import my.projects.spacerangers2.game.objects.SpaceObjectBuilder;

public class GameLevel extends Task<Boolean>{
	private Stage stage;
	private SpaceEntityBuilder entityBuilder;
	private Thread[] entityThreads;
	
	public GameLevel(Stage stage) {
		this.stage = stage;
	}

	public void build() {
		BorderPane rootNode = new BorderPane();
		Vector2D sceneSize = new Vector2D( 800, 600);
		int n = 2000;
		AimableModifiableList aimableList = new AimableSkipList();
		SpaceObjectBuilder objectBuilder = new SpaceObjectBuilder(rootNode); 
		entityBuilder = new SpaceEntityBuilder(sceneSize, aimableList, objectBuilder);
		entityThreads = new Thread[n];
		for(int i = 0;i<n;i++) {
			double left = Math.random()*(sceneSize.x-50);
			double right = Math.random()*(sceneSize.y-50);
			double speed = Math.random()*10 + 2;
			Asteroid asteroid = entityBuilder.makeAsteroid(left, right, speed, 5, 1000);
			entityThreads[i] = new Thread(asteroid);
		}
		
        Scene scene = new Scene(rootNode, sceneSize.x, sceneSize.y);
//        scene.getStylesheets().add("/styles/styles.css");
        stage.setScene(scene);
        stage.show();
		
	}

	public Boolean start() {
		SynchronizationManager manager = entityBuilder.getSynchronizationManager();
		
		AnimationTimer at = new AnimationTimer() {

			@Override
			public void handle(long now) {
				manager.sendAnimationTick();
			}
		};
		Voice backgroundMusic = new Voice(MusicsBuilder.getInstance().get(StreamName.LONG_WAY_BALLADE));
		backgroundMusic.startContinuousPlaying();
		manager.enableScene();
		at.start();
		manager.resumeScene();
		for(Thread t : entityThreads) {
			t.setDaemon(true);
			t.start();
			try {
				TimeUnit.MILLISECONDS.sleep((long)(Math.random()*20) + 20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		boolean shipIsAlive = manager.waitForModuleEndAndGetIsShipAlive();
		backgroundMusic.stop();
		backgroundMusic.close();
		return shipIsAlive;
	}

	@Override
	protected Boolean call() throws Exception {
		return start();
	}
}
