package tests.io.github.socraticphoenix.jamfx;

import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.jamfx.JamEnvironment;
import io.github.socraticphoenix.jamfx.JamProperties;
import io.github.socraticphoenix.jamfx.event.FXSource;
import io.github.socraticphoenix.jamfx.event.Jam;
import io.github.socraticphoenix.occurrence.annotation.Listener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class HelloWorld extends JamController {

    @FXML @Jam private Button hi;

    public HelloWorld(JamEnvironment environment, JamProperties properties, Scene scene) {
        super(environment, properties, scene);
    }

    @Listener
    public void onAction(ActionEvent ev, @FXSource("hi") Button button) {
        System.out.println(button);
    }

}
