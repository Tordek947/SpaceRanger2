package my.projects.spacerangers2.game.scene;


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
import my.projects.spacerangers2.game.concurrent.GameLevelSynchronizer;
import my.projects.spacerangers2.game.entities.SpaceEntityCreator;
import my.projects.spacerangers2.game.geometry.Vector2D;
import my.projects.spacerangers2.game.objects.SpaceObjectBuilder;

public class GameLevel extends Task<Boolean>{
	private Stage stage;
	private Voice backgroundMusic;
	private GameLevelSynchronizer levelSynchronizer;
	private GameModule[] gameModules;
	private int modulesCount;
	
	public GameLevel(Stage stage) {
		this.stage = stage;
	}

	public void build() {
        backgroundMusic = new Voice(MusicsBuilder.getInstance().get(StreamName.LONG_WAY_BALLADE));
		Pane rootNode = new BorderPane();
		Vector2D sceneSize = new Vector2D( 800, 600);
		levelSynchronizer = new GameLevelSynchronizer();
		AimableModifiableList aimableList = new AimableSkipList();
		SpaceObjectBuilder objectBuilder = new SpaceObjectBuilder(rootNode);
		modulesCount = 2;
		gameModules = new GameModule[modulesCount];
        Scene scene = new Scene(rootNode, sceneSize.x, sceneSize.y);
//        scene.getStylesheets().add("/styles/styles.css");
        gameModules[0] = new TestModule(sceneSize, new SpaceEntityCreator(sceneSize, aimableList, objectBuilder, levelSynchronizer));
        gameModules[1] = new TestModule(sceneSize, new SpaceEntityCreator(sceneSize, aimableList, objectBuilder, levelSynchronizer));
        gameModules[0].build();
        stage.setScene(scene);
        stage.setTitle("SpaceRanger 2.0");
        stage.show();
		backgroundMusic.startContinuousPlaying();
	}


	@Override
	protected Boolean call() throws Exception {
		AnimationTimer at = new AnimationTimer() {

			@Override
			public void handle(long now) {
				levelSynchronizer.sendAnimationTick();
			}
		};
		
		levelSynchronizer.enableScene();
		at.start();
		levelSynchronizer.resumeScene();
		for(int i = 0;i<modulesCount-1;i++) {
			gameModules[i+1].buildInBackground();
			gameModules[i].execute();
			if (gameModules[i].isShipAlive() == false) {
				return levelFinishing(false);
			}
		}
		gameModules[modulesCount-1].execute();
		return levelFinishing(gameModules[modulesCount-1].isShipAlive());
	}

	private boolean levelFinishing(boolean isShipAlive) {
		levelSynchronizer.disableScene();
		backgroundMusic.stop();
		backgroundMusic.close();
		return isShipAlive;
	}
}
