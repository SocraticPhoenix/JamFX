package io.github.socraticphoenix.jamfx;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JamProperty {

    String PREVIOUS_CONTROLLER = "previous_controller";
    String PREVIOUS_PROPERTIES = "parent_properties";
    String PREVIOUS_URL = "parent_url";

    String PARENT_CONTROLLER = "parent_controller";
    String URL = "url";

    String value();

}
