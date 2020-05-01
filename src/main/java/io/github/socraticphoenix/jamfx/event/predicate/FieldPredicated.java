package io.github.socraticphoenix.jamfx.event.predicate;


import io.github.socraticphoenix.occurrence.functional.ListenerPredicate;
import io.github.socraticphoenix.occurrence.reflection.JavaType;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

public class FieldPredicated implements ListenerPredicate<Object> {
    private Field field;
    private Object owner;

    public FieldPredicated(Field field, Object owner) {
        this.field = field;
        this.owner = owner;
    }

    @Override
    public Boolean apply(Object object, Object... params) {
        try {
            this.field.setAccessible(true);
            return this.field.get(this.owner) == object;
        } catch (IllegalAccessException e) {
            return false;
        }
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
