package io.github.socraticphoenix.jamfx.event.predicate;


import io.github.socraticphoenix.occurrence.functional.ListenerPredicate;
import io.github.socraticphoenix.occurrence.reflection.JavaType;

import java.util.Collections;
import java.util.List;

public class AlwaysTruePredicate implements ListenerPredicate<Object> {

    @Override
    public Boolean apply(Object object, Object... params) {
        return true;
    }

    @Override
    public Class<Object> inputType() {
        return Object.class;
    }

    @Override
    public JavaType returnType() {
        return JavaType.of(Boolean.class);
    }

    @Override
    public List<Class> parameterTypes() {
        return Collections.emptyList();
    }

}
