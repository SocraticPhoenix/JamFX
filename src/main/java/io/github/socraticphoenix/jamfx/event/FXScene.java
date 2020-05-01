package io.github.socraticphoenix.jamfx.event;


@io.github.socraticphoenix.jamfx.event.FXScene.$_0
@java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface FXScene {

    @io.github.socraticphoenix.jamfx.event.FXScene.$_0.$_1
    @io.github.socraticphoenix.jamfx.event.FXScene.$_0.$_2
    @io.github.socraticphoenix.jamfx.event.FXScene.$_0.$_3
    @java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
    @interface $_0 {

        @io.github.socraticphoenix.occurrence.annotation.Filter.Invoke(value = "getSource")
        @java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
        @interface $_1 {

        }

        @io.github.socraticphoenix.occurrence.annotation.Filter.Include(value = {javafx.scene.Node.class})
        @java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
        @interface $_2 {

        }

        @io.github.socraticphoenix.occurrence.annotation.Filter.Invoke(value = "getScene")
        @java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
        @interface $_3 {

        }

    }

}
