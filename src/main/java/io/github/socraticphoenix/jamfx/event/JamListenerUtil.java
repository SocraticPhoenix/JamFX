package io.github.socraticphoenix.jamfx.event;

import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.jamfx.JamLoadException;
import io.github.socraticphoenix.occurence.ListenerWrapper;
import io.github.socraticphoenix.occurence.annotation.meta.AnnotationWrapper;
import io.github.socraticphoenix.occurence.generator.ListenerWrapperGenerator;
import io.github.socraticphoenix.occurence.generator.WrapperPolicyRegistry;
import io.github.socraticphoenix.occurence.generator.reflection.ReflectionWrapperGenerator;
import javafx.event.Event;
import javafx.event.EventHandler;
import lombok.With;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class JamListenerUtil {

    public static final ListenerWrapperGenerator<Event> GENERATOR;

    static {
        ReflectionWrapperGenerator<Event> generator = new ReflectionWrapperGenerator<>(new WrapperPolicyRegistry(), Event.class);
        generator.getRegistry().registerDefaults();
        GENERATOR = generator;
    }

    public static void apply(JamController controller) {
        GENERATOR.createWrappers(controller).forEach(wrapper -> {
            Optional<JamBinding> binding = wrapper.annotations().stream().map(AnnotationWrapper::getAnnotation).filter(a -> a instanceof JamBinding).map(a -> (JamBinding) a).findFirst();
            if (binding.isPresent()) {
                bind(binding.get(), wrapper, controller);
            } else {
                throw new IllegalArgumentException(wrapper.name() + " must have JamBinding annotation");
            }
        });
    }

    private static void bind(JamBinding binding, ListenerWrapper<Event> wrapper, JamController controller) {
        Class<? extends JamController> cls = controller.getClass();

        try {
            Field field = cls.getDeclaredField(binding.fxid());
            field.setAccessible(true);

            Object value = field.get(controller);
            Class<?> fieldClass = value.getClass();

            fieldClass.getMethod(binding.event(), EventHandler.class).invoke(value, (EventHandler<Event>) wrapper::handleSafe);
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new JamLoadException("Failed to load event binding", e);
        }

    }

}
