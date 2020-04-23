package io.github.socraticphoenix.jamfx;

import com.gmail.socraticphoenix.collect.coupling.Pair;
import io.github.socraticphoenix.jamfx.event.JamListenerUtil;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

public abstract class JamController {
    @Getter private JamProperties properties;
    @Getter private Scene scene;

    public JamController(JamProperties properties, Scene scene) {
        this.properties = properties;
        this.scene = scene;
    }

    public JamController(Scene scene) {
        this(new JamProperties(), scene);
    }

    public void init() {
        JamListenerUtil.apply(this);
    }

    public <T extends JamController> Pair<T, Stage> popup(String name) {
        return this.popup(name, Modality.APPLICATION_MODAL, this.properties);
    }

    public <T extends JamController> Pair<T, Stage> popup(String name, Modality modality, JamProperties properties) {
        Stage newStage = new Stage();
        Scene scene = new Scene(new AnchorPane());
        newStage.setScene(scene);

        newStage.initModality(modality);
        newStage.initOwner(this.scene.getWindow());

        Pair<T, Pane> loaded = load(name, scene, properties);
        scene.setRoot(loaded.getB());

        return Pair.of(loaded.getA(), newStage);
    }

    public <T extends JamController> T switchView(String name) {
        return this.switchView(name, this.properties);
    }

    public <T extends JamController> T switchView(String name, JamProperties properties) {
        Pair<T, Pane> loaded = load(name, this.scene, properties);
        this.scene.setRoot(loaded.getB());
        return loaded.getA();
    }

    public <T extends JamController> Pair<T, Pane> load(String name, Scene scene, JamProperties properties) {
        return this.load(getClass().getResource(name), scene, properties);
    }

    public static <T extends JamController> Pair<T, Stage> loadStage(URL fxml, JamProperties properties) {
        Stage newStage = new Stage();
        Scene scene = new Scene(new AnchorPane());
        newStage.setScene(scene);

        Pair<T, Pane> loaded = load(fxml, scene, properties);
        scene.setRoot(loaded.getB());

        return Pair.of(loaded.getA(), newStage);
    }

    public static <T extends JamController> Pair<T, Pane> load(URL fxml, Scene scene, JamProperties properties) {
        try {
            FXMLLoader loader = new FXMLLoader(fxml);
            loader.setControllerFactory(cls -> {
                try {
                    Constructor<?> cons = cls.getConstructor(JamProperties.class, Scene.class);
                    return cons.newInstance(properties, scene);
                } catch (NoSuchMethodException e) {
                    try {
                        Constructor<?> cons2 = cls.getConstructor(Scene.class);
                        return cons2.newInstance(scene);
                    } catch (NoSuchMethodException
                            | IllegalAccessException
                            | InstantiationException
                            | InvocationTargetException ex) {
                        throw new RuntimeException("Failed to call (Scene) constructor", ex);
                    }
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    throw new RuntimeException("Failed to call (JamProperties, Scene) constructor", e);
                }
            });
            Pane root = loader.load();
            T controller = loader.getController();
            controller.init();
            return Pair.of(controller, root);
        } catch (IOException e) {
            throw new JamLoadException("Failed to load FXML: " + fxml, e);
        }
    }

}
