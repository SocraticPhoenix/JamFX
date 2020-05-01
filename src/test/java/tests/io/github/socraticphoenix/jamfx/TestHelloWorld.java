package tests.io.github.socraticphoenix.jamfx;

import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.jamfx.JamEnvironment;
import io.github.socraticphoenix.jamfx.JamProperties;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestHelloWorld extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        JamController.loadStage(getClass().getResource("helloWorld.fxml"), new JamEnvironment(), new JamProperties()).getValue().show();
    }

}
