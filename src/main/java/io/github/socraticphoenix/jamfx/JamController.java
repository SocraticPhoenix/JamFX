package io.github.socraticphoenix.jamfx;

import io.github.socraticphoenix.jamfx.event.Jam;
import io.github.socraticphoenix.jamfx.event.predicate.FieldPredicated;
import io.github.socraticphoenix.occurrence.generator.WrapperPolicyRegistry;
import io.github.socraticphoenix.occurrence.generator.reflection.ReflectionWrapperGenerator;
import io.github.socraticphoenix.occurrence.manager.EventManager;
import io.github.socraticphoenix.occurrence.manager.dispatch.SimpleEventDispatch;
import io.github.socraticphoenix.occurrence.manager.dispatch.SynchronizedEventDispatch;
import io.github.socraticphoenix.occurrence.manager.listener.EventListeners;
import io.github.socraticphoenix.occurrence.manager.listener.SimpleEventListeners;
import io.github.socraticphoenix.occurrence.manager.listener.SynchronizedEventListeners;
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
import javafx.util.Pair;
import lombok.Getter;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class JamController {
    @Getter private JamProperties properties;
    @Getter private Scene scene;
    @Getter private JamEnvironment environment;

    public JamController(JamEnvironment environment, JamProperties properties, Scene scene) {
        this.properties = properties;
        this.scene = scene;
        this.environment = environment;
    }

    public void refresh() {
        this.properties.get(JamProperty.PARENT_CONTROLLER, JamController.class).ifPresent(JamController::refresh);
    }

    public void init() {
        WrapperPolicyRegistry policies = new WrapperPolicyRegistry();
        ReflectionWrapperGenerator<Object> generator = new ReflectionWrapperGenerator<>(Object.class);
        generator.getRegistry().registerDefaults();

        EventListeners<Object> listeners = new SynchronizedEventListeners<>(this.environment, new SimpleEventListeners(generator, policies));
        EventManager<Object> manager = new EventManager<>(new SynchronizedEventDispatch<>(this.environment, new SimpleEventDispatch<>()), listeners);

        List<Field> targets = new ArrayList<>();
        discoverFields(getClass(), f -> {
            if (f.isAnnotationPresent(Jam.class) && policies.predicateFor(f.getName()).isEmpty()) {
                policies.registerPredicate(f.getName(), new FieldPredicated(f, this));
                targets.add(f);
            }

            if (f.isAnnotationPresent(JamProperty.class)) {
                f.setAccessible(true);
                try {
                    JamProperty property = f.getAnnotation(JamProperty.class);
                    if (property.optional()) {
                        f.set(this, this.properties.get(property.value(), f.getType()).orElse(null));
                    } else {
                        f.set(this, this.properties.require(property.value(), f.getType()));
                    }
                } catch (IllegalAccessException e) {
                    throw new JamLoadException("Unable to access @JamProperty annotated field: " + f.getName(), e);
                } catch (JamPropertyRequiredException e) {
                    throw new JamLoadException("Unable to set @JamProperty annotated field: " + f.getName(), e);
                }
            }
        });

        for (Field field : targets) {
            try {
                field.setAccessible(true);
                Object val = field.get(this);
                EventHandler<Event> handler = manager::post;

                if (val instanceof Node) {
                    ((Node) val).addEventHandler(Event.ANY, handler);
                } else if (val instanceof Window) {
                    ((Window) val).addEventHandler(Event.ANY, handler);
                } else if (val instanceof Scene) {
                    ((Scene) val).addEventHandler(Event.ANY, handler);
                }
            } catch (IllegalAccessException e) {
                throw new JamLoadException("Unable to access @Jam annotated field: " + field.getName(), e);
            }
        }

        listeners.add(this);
        this.environment.add(listeners);
    }

    private void discoverFields(Class<?> cls, Consumer<Field> consumer) {
        if (cls != null) {
            for (Field field : cls.getDeclaredFields()) {
                consumer.accept(field);
            }
            discoverFields(cls.getSuperclass(), consumer);
        }
    }


    public <T extends JamController> Pair<T, Stage> popup(String name) {
        return this.popup(name, Modality.APPLICATION_MODAL, this.makePopupProperties());
    }

    public <T extends JamController> Pair<T, Stage> popup(String name, Modality modality, JamProperties properties) {
        return this.popup(getClass().getResource(name), modality, properties);
    }

    public <T extends JamController> Pair<T, Stage> popup(URL fxml, Modality modality, JamProperties properties) {
        Stage newStage = new Stage();
        Scene scene = new Scene(new AnchorPane());
        newStage.setScene(scene);

        newStage.initModality(modality);
        newStage.initOwner(this.scene.getWindow());

        Pair<T, Pane> loaded = load(fxml, scene, this.environment, properties);
        scene.setRoot(loaded.getValue());

        return new Pair<>(loaded.getKey(), newStage);
    }

    public <T extends JamController> T switchView(String name) {
        return this.switchView(name, this.makeChildProperties());
    }

    public <T extends JamController> T switchView(String name, JamProperties properties) {
        return this.switchView(getClass().getResource(name), properties);
    }


    public <T extends JamController> T switchView(URL fxml, JamProperties properties) {
        Pair<T, Pane> loaded = load(fxml, this.scene, this.environment, properties);
        this.scene.setRoot(loaded.getValue());
        return loaded.getKey();
    }

    public JamProperties makeChildProperties() {
        JamProperties properties = this.properties.copy();
        properties.put(JamProperty.PREVIOUS_CONTROLLER, this);
        properties.put(JamProperty.PREVIOUS_PROPERTIES, this.properties);

        this.properties.get(JamProperty.URL).ifPresent(u -> properties.put(JamProperty.PREVIOUS_URL, u));

        return properties;
    }

    public JamProperties makePopupProperties() {
        return makeChildProperties().put(JamProperty.PARENT_CONTROLLER, this);
    }

    public static <T extends JamController> Pair<T, Stage> loadStage(URL fxml,  JamEnvironment environment, JamProperties properties) {
        Stage newStage = new Stage();
        Scene scene = new Scene(new AnchorPane());
        newStage.setScene(scene);

        Pair<T, Pane> loaded = load(fxml, scene, environment, properties);
        scene.setRoot(loaded.getValue());

        return new Pair<>(loaded.getKey(), newStage);
    }

    public static <T extends JamController> Pair<T, Pane> load(URL fxml, Scene scene, JamEnvironment environment, JamProperties properties) {
        try {
            FXMLLoader loader = new FXMLLoader(fxml);
            properties = properties.copy().put(JamProperty.URL, fxml);
            JamProperties finalProperties = properties;
            loader.setControllerFactory(cls -> {
                try {
                    Constructor<?> cons = cls.getConstructor(JamEnvironment.class, JamProperties.class, Scene.class);
                    return cons.newInstance(environment, finalProperties, scene);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException("Required constructor does not exist: (JamEnvironment, JamProperties, Scene)", e);
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                    throw new RuntimeException("Failed to call (JamProperties, Scene) constructor", e);
                }
            });
            Pane root = loader.load();
            T controller = loader.getController();
            controller.init();
            return new Pair<>(controller, root);
        } catch (IOException e) {
            throw new JamLoadException("Failed to load FXML: " + fxml, e);
        }
    }

}
