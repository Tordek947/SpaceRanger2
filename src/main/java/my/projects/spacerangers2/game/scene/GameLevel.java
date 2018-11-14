package my.projects.spacerangers2.game.scene;



import java.util.concurrent.Semaphore;

import javafx.animation.AnimationTimer;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import my.projects.resources.MusicsBuilder;
import my.projects.resources.MusicsBuilder.StreamName;
import my.projects.spacerangers2.game.common.Voice;
import my.projects.spacerangers2.game.concurrent.AimableModifiableList;
import my.projects.spacerangers2.game.concurrent.AimableSkipList;
import my.projects.spacerangers2.game.concurrent.GameLevelSynchronizer;
import my.projects.spacerangers2.game.entities.SpaceEntityCreator;
import my.projects.spacerangers2.game.entities.UserShip;
import my.projects.spacerangers2.game.entities.Weapon;
import my.projects.spacerangers2.game.geometry.Vector2D;
import my.projects.spacerangers2.game.objects.SpaceObjectBuilder;

public class GameLevel extends Task<Boolean>{
	private Stage stage;
	private Voice backgroundMusic;
	private GameLevelSynchronizer levelSynchronizer;
	private GameModule[] gameModules;
	private int modulesCount;
	private AnimationTimer at; 
	private UserShip userShip;
	private Semaphore s;
	private boolean isPause;
	
	public GameLevel(Stage stage) {
		this.stage = stage;
	}

	public void build() {
        backgroundMusic = new Voice(MusicsBuilder.getInstance().get(StreamName.LONG_WAY_BALLADE));
		Pane rootNode = new BorderPane();
		stage.setFullScreenExitKeyCombination(KeyCombination.keyCombination("Q"));
		stage.setFullScreenExitHint(
				"Press the Space to start a game, escape to pause, Q to exit a fullscreen mode");
		stage.setFullScreen(true);
		Vector2D sceneSize = getScreenMaxSize();
		levelSynchronizer = new GameLevelSynchronizer();
		AimableModifiableList aimableList = new AimableSkipList();
		SpaceObjectBuilder.initializeInstance(rootNode);
		SpaceEntityCreator entityCreator = new SpaceEntityCreator(sceneSize, aimableList, levelSynchronizer, rootNode);
		userShip = entityCreator.makeUserShip(6, 180);
		addUserShipKeyEventHandler();
		Weapon weapon = entityCreator.makeWeapon(10, 7, 100);
		userShip.setWeapon(weapon);
		modulesCount = 3;
		gameModules = new GameModule[modulesCount];
        Scene scene = new Scene(rootNode, sceneSize.x, sceneSize.y);
//        scene.getStylesheets().add("/styles/styles.css");
        s = new Semaphore(0);
        for(int i = 0;i<modulesCount;i++) {
    		gameModules[i] = new TestModule(sceneSize, entityCreator, s);
        }
        gameModules[0].build();
        stage.setScene(scene);
        stage.setTitle("SpaceRanger 2.0");
        stage.show();
		backgroundMusic.startContinuousPlaying();

	}

	
	protected Vector2D getScreenMaxSize() {
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
		return new Vector2D(primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight());
	}

	private void addUserShipKeyEventHandler() {
		stage.addEventHandler(KeyEvent.KEY_PRESSED, userShip.getKeyEventHandler());
	}
	
	@Override
	protected Boolean call() throws Exception {
		at = new AnimationTimer() {

			@Override
			public void handle(long now) {
				levelSynchronizer.sendAnimationTick();
			}
		};
		levelSynchronizer.enableScene();
		at.start();
		levelSynchronizer.resumeScene();
		addPauseKeyEventHandler();
		Thread t = new Thread(()->{
			userShip.run();
			s.release(1000);
		});
		t.setDaemon(true);
		t.start();
		for(int i = 0;i<modulesCount-1;i++) {
			gameModules[i+1].buildInBackground();
			gameModules[i].execute();
			if (userShip.isDead()) {
				return levelFinishing(false);
			}
		}
		gameModules[modulesCount-1].execute();
		return levelFinishing(userShip.isAlive());
	}

	private void addPauseKeyEventHandler() {
		stage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				final KeyCombination pause = new KeyCodeCombination(KeyCode.ESCAPE);
				if (pause.match(event)) {
					if (isPause) {
						levelSynchronizer.resumeScene();
						isPause = false;
					} else {
						levelSynchronizer.pauseScene();
						isPause = true;
					}
				}
			}
			
		});
	}

	private boolean levelFinishing(boolean isShipAlive) {
		levelSynchronizer.disableScene();
		backgroundMusic.stop();
		backgroundMusic.close();
		at.stop();
		return isShipAlive;
	}

}
