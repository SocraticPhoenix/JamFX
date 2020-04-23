package tests.io.github.socraticphoenix.jamfx;

import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.jamfx.JamProperties;
import io.github.socraticphoenix.jamfx.event.FXScene;
import io.github.socraticphoenix.jamfx.event.FXSource;
import io.github.socraticphoenix.jamfx.event.Jam;
import io.github.socraticphoenix.occurence.annotation.Listener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class HelloWorld extends JamController {

    @FXML @Jam private Button hi;

    public HelloWorld(JamProperties properties, Scene scene) {
        super(properties, scene);
    }

    @Listener
    public void onAction(ActionEvent event, @FXScene Scene scene, @FXSource("hi") Button object) {
        System.out.println(scene);
        System.out.println(object);
    }

}
