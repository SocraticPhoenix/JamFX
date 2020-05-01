package io.github.socraticphoenix.jamfx.event;


@io.github.socraticphoenix.jamfx.event.FXSource.$_4
@io.github.socraticphoenix.occurrence.annotation.meta.Delegate(value = {"io.github.socraticphoenix.jamfx.event.FXSource.$_4:id->value"})
@java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface FXSource {

    java.lang.String value() default "<always_true>";

    @io.github.socraticphoenix.jamfx.event.FXSource.$_4.$_5
    @io.github.socraticphoenix.jamfx.event.FXSource.$_4.$_6
    @io.github.socraticphoenix.occurrence.annotation.meta.Delegate(value = {"io.github.socraticphoenix.jamfx.event.FXSource.$_4.$_6:id->id"})
    @java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
    @interface $_4 {

        @io.github.socraticphoenix.occurrence.annotation.Filter.Invoke(value = "getSource")
        @java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
        @interface $_5 {

        }

        @io.github.socraticphoenix.occurrence.annotation.Filter.Predicate
        @io.github.socraticphoenix.occurrence.annotation.meta.Delegate(value = {"io.github.socraticphoenix.occurrence.annotation.Filter.Predicate:id->id"})
        @java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
        @interface $_6 {

        }

    }

}
