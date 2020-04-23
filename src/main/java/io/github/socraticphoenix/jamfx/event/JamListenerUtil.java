package io.github.socraticphoenix.jamfx.event;

import io.github.socraticphoenix.jamfx.JamController;
import io.github.socraticphoenix.occurence.generator.ListenerWrapperGenerator;
import io.github.socraticphoenix.occurence.generator.WrapperPolicyRegistry;
import io.github.socraticphoenix.occurence.generator.reflection.ReflectionWrapperGenerator;
import javafx.event.Event;

public class JamListenerUtil {

    public static final ListenerWrapperGenerator<Event> GENERATOR;

    static {
        ReflectionWrapperGenerator<Event> generator = new ReflectionWrapperGenerator<>(new WrapperPolicyRegistry(), Event.class);
        generator.getRegistry().registerDefaults();
        GENERATOR = generator;
    }

    public static void apply(JamController controller) {

    }

}
