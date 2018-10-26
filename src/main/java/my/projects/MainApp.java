package my.projects;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import my.projects.resources.ImagesBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(Stage stage) throws Exception {

        log.info("Starting Hello JavaFX and Maven demonstration application");

        GridPane rootNode = new GridPane();
        Button start = new Button("Play test!");
        start.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				buildAndShowGameStage();
			}
        	
        });
        rootNode.add(start, 0, 0);
        ImageView window = new ImageView();
        ImagesBuilder ib = ImagesBuilder.getInstance();
        Image img = ib.getWeaponImage();
        window.setImage(img);
        rootNode.add(window, 0, 1);

        log.debug("Showing JFX scene");
        Scene scene = new Scene(rootNode, 400, 200);
        scene.getStylesheets().add("/styles/styles.css");
        
        stage.setTitle("SpaceRanger 2.0");
        stage.setScene(scene);
        stage.show();
    }

	protected void buildAndShowGameStage() {
		System.out.println("Is working?");
	}
}
