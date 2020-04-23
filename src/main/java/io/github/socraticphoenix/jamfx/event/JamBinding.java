package io.github.socraticphoenix.jamfx.event;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JamBinding {

    String fxid();

    String event() default "setOnAction";

}
