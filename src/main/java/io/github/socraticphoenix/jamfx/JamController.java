package io.github.socraticphoenix.jamfx;

import com.gmail.socraticphoenix.collect.coupling.Pair;
import io.github.socraticphoenix.jamfx.event.predicate.AlwaysTruePredicate;
import io.github.socraticphoenix.jamfx.event.predicate.FieldPredicated;
import io.github.socraticphoenix.jamfx.event.Jam;
import io.github.socraticphoenix.occurence.generator.WrapperPolicyRegistry;
import io.github.socraticphoenix.occurence.generator.reflection.ReflectionWrapperGenerator;
import io.github.socraticphoenix.occurence.manager.EventManager;
import io.github.socraticphoenix.occurence.manager.SimpleEventManager;
import io.github.socraticphoenix.occurence.manager.SynchronizedEventManager;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Getter;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

public abstract class JamController {
    @Getter private JamProperties properties;
    @Getter private Scene scene;

    private EventManager<Event> manager;
    private ReflectionWrapperGenerator<Event> generator;

    public JamController(JamProperties properties, Scene scene) {
        this.properties = properties;
        this.scene = scene;
    }

    public JamController(Scene scene) {
        this(new JamProperties(), scene);
    }

    public void init() {
        ReflectionWrapperGenerator<Event> generator = new ReflectionWrapperGenerator<>(new WrapperPolicyRegistry(), Event.class);
        generator.getRegistry().registerDefaults();
        this.manager = new SynchronizedEventManager<>(new SimpleEventManager<>(generator));
        this.generator = generator;

        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Jam.class)) {
                field.setAccessible(true);
                try {
                    Object val = field.get(this);
                    EventHandler<Event> handler = this.manager::postSafely;

                    if (val instanceof Node) {
                        ((Node) val).addEventHandler(Event.ANY, handler);
                    } else if (val instanceof Window) {
                        ((Window) val).addEventHandler(Event.ANY, handler);
                    } else if (val instanceof Scene) {
                        ((Scene) val).addEventHandler(Event.ANY, handler);
                    }
                } catch (IllegalAccessException e) {
                    throw new JamLoadException("Unable to access Jam annotated field: " + field.getName(), e);
                }

                generator.policies().registerPredicate(field.getName(), new FieldPredicated(field, this));
            }
        }
        generator.policies().registerPredicate("<always_true>", new AlwaysTruePredicate());

        this.manager.register(this);
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
