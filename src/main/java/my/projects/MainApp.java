package my.projects;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import my.projects.resources.MusicsBuilder;
import my.projects.resources.MusicsBuilder.StreamName;
import my.projects.spacerangers2.game.common.Voice;
import my.projects.spacerangers2.game.scene.GameLevel;

import java.util.concurrent.ExecutionException;

public class MainApp extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
		buildAndShowGameStage(stage);
    }

	protected void buildAndShowGameStage(Stage stage) {
		GameLevel gameLevel = new GameLevel(stage);
		gameLevel.build();
		gameLevel.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent event) {
				try {
					boolean shipIsAlive = gameLevel.get();
					Voice v = new Voice();
					v.openClip(MusicsBuilder.getInstance().get(shipIsAlive ? StreamName.VICTORY
																			: StreamName.DEFEAT));
					v.start();
					Dialog<ButtonType> d = new Alert(AlertType.INFORMATION);
					d.setHeaderText(shipIsAlive ? "You WON!" : "You LOOSE!");
					d.setOnHiding((e)->Platform.exit());
					d.showAndWait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
			
		});
		Thread t = new Thread(gameLevel);
		t.setDaemon(true);
        EventType<KeyEvent> eType = KeyEvent.KEY_PRESSED;
		stage.addEventHandler(eType, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() ==  KeyCode.SPACE) {
					stage.removeEventHandler(eType, this);
					t.start();
				}
			}
			
		});
	}
}
