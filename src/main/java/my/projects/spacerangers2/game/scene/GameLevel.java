package my.projects.spacerangers2.game.scene;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import my.projects.resources.MusicsBuilder;
import my.projects.spacerangers2.game.common.Voice;
import my.projects.spacerangers2.game.concurrent.AimableList;
import my.projects.spacerangers2.game.concurrent.AimableSkipList;
import my.projects.spacerangers2.game.concurrent.SynchronizationManager;
import my.projects.spacerangers2.game.entities.Asteroid;
import my.projects.spacerangers2.game.entities.SpaceEntityBuilder;
import my.projects.spacerangers2.game.geometry.Vector2D;
import my.projects.spacerangers2.game.objects.SpaceObjectBuilder;

public class GameLevel {
	private Stage stage;
	private SpaceEntityBuilder entityBuilder;
	private Thread[] entityThreads;
	
	public GameLevel(Stage stage) {
		this.stage = stage;
	}

	public void build() {
		BorderPane rootNode = new BorderPane();
		Vector2D sceneSize = new Vector2D( 800, 600);
		
		AimableList aimableList = new AimableSkipList();
		SpaceObjectBuilder objectBuilder = new SpaceObjectBuilder(rootNode); 
		entityBuilder = new SpaceEntityBuilder(sceneSize, aimableList, objectBuilder);
		Asteroid asteroid = entityBuilder.makeAsteroid(50, 50, 5, 5, 300);
		entityThreads = new Thread[1];
		entityThreads[0] = new Thread(asteroid);
		
        Scene scene = new Scene(rootNode, sceneSize.x, sceneSize.y);
//        scene.getStylesheets().add("/styles/styles.css");
        stage.setScene(scene);
        stage.show();
		
	}

	public void start() {
		SynchronizationManager manager = entityBuilder.getSynchronizationManager();
		
		AnimationTimer at = new AnimationTimer() {

			@Override
			public void handle(long now) {
				manager.sendAnimationTick();
			}
		};
		
		for(Thread t : entityThreads) {
			t.setDaemon(true);
			t.start();
		}
		
		manager.enableScene();
		at.start();
		manager.resumeScene();
		manager.aquireAllDeadEnemyTokens();
	}
}
