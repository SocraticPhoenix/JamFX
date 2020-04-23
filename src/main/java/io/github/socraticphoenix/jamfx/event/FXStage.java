package io.github.socraticphoenix.jamfx.event;


@io.github.socraticphoenix.jamfx.event.FXStage.$_7
@java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface FXStage {

    @java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
    @io.github.socraticphoenix.jamfx.event.FXStage.$_7.$_8
    @io.github.socraticphoenix.jamfx.event.FXStage.$_7.$_9
    @io.github.socraticphoenix.jamfx.event.FXStage.$_7.$_10
    @io.github.socraticphoenix.jamfx.event.FXStage.$_7.$_11
    @io.github.socraticphoenix.jamfx.event.FXStage.$_7.$_12
    @interface $_7 {

        @io.github.socraticphoenix.occurence.annotation.Filter.Invoke(value = "getSource")
        @java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
        @interface $_8 {

        }

        @io.github.socraticphoenix.occurence.annotation.Filter.Include(value = {javafx.scene.Node.class})
        @java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
        @interface $_9 {

        }

        @io.github.socraticphoenix.occurence.annotation.Filter.Invoke(value = "getScene")
        @java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
        @interface $_10 {

        }

        @io.github.socraticphoenix.occurence.annotation.Filter.Invoke(value = "getWindow")
        @java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
        @interface $_11 {

        }

        @io.github.socraticphoenix.occurence.annotation.Filter.Include(value = {javafx.stage.Stage.class})
        @java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
        @interface $_12 {

        }

    }

}
