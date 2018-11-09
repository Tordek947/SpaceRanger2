package my.projects.spacerangers2.game.scene;

import java.util.concurrent.TimeUnit;

import javafx.animation.AnimationTimer;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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
	private AimableModifiableList aimableList;
	private Asteroid[] asteroids;
	private Asteroid userShip;
	private Voice backgroundMusic;
	
	public GameLevel(Stage stage) {
		this.stage = stage;
	}

	public void build() {
        backgroundMusic = new Voice(MusicsBuilder.getInstance().get(StreamName.LONG_WAY_BALLADE));
		Pane rootNode = new BorderPane();
		Vector2D sceneSize = new Vector2D( 800, 600);
		int n = 500;
		aimableList = new AimableSkipList();
		SpaceObjectBuilder objectBuilder = new SpaceObjectBuilder(rootNode); 
		entityBuilder = new SpaceEntityBuilder(sceneSize, aimableList, objectBuilder);
		asteroids = new Asteroid[n];
		for(int i = 0;i<n;i++) {
			double left = Math.random()*(sceneSize.x-50);
			double right = Math.random()*(sceneSize.y-50);
			double speed = Math.random()*10 + 2;
			asteroids[i] = entityBuilder.makeAsteroid(left, right, speed, 50, 500);
		}
//		userShip = entityBuilder.makeUserShip();

        Scene scene = new Scene(rootNode, sceneSize.x, sceneSize.y);
//        scene.getStylesheets().add("/styles/styles.css");
        stage.setScene(scene);
        stage.setTitle("SpaceRanger 2.0");
        stage.show();
		backgroundMusic.startContinuousPlaying();
	}


	@Override
	protected Boolean call() throws Exception {
		SynchronizationManager manager = entityBuilder.getSynchronizationManager();
		AnimationTimer at = new AnimationTimer() {

			@Override
			public void handle(long now) {
				manager.sendAnimationTick();
			}
		};
		
		manager.enableScene();
		at.start();
		manager.resumeScene();
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
//		Thread t = new Thread(userShip);
//		t.setDaemon(true);
//		aimableList.setAimableUserShip(userShip);
//		t.start();
		
		boolean shipIsAlive = manager.waitForFightEndAndGetIsShipAlive();
		manager.disableScene();
		backgroundMusic.stop();
		backgroundMusic.close();
		return shipIsAlive;
	}
}
