package tests.io.github.socraticphoenix.jamfx;

import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.jamfx.JamProperties;
import io.github.socraticphoenix.jamfx.event.FXScene;
import io.github.socraticphoenix.jamfx.event.JamBinding;
import io.github.socraticphoenix.occurence.annotation.Listener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class HelloWorld extends JamController {

    @FXML
    private Button hi;

    public HelloWorld(JamProperties properties, Scene scene) {
        super(properties, scene);
    }

    @Listener
    @JamBinding(fxid = "hi")
    public void onAction(ActionEvent event, @FXScene Scene scene, @DelegateTest("getSource") Object object) {
        System.out.println(scene);
        System.out.println(object);
    }

}
