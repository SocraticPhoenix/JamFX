package tests.io.github.socraticphoenix.jamfx;

import io.github.socraticphoenix.occurrence.annotation.Filter;
import io.github.socraticphoenix.occurrence.annotation.meta.Delegate;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Filter.Invoke
@Delegate("io.github.socraticphoenix.occurence.annotation.Filter.Invoke:value->value")
public @interface DelegateTest {

    String value();

}
