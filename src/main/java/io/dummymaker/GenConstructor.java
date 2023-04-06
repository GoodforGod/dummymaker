package io.dummymaker;

import io.dummymaker.error.GenConstructionException;
import java.lang.reflect.Constructor;
import java.util.List;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 23.03.2023
 */
final class GenConstructor {

    private final GenType type;
    private final Constructor<?> constructor;
    private final List<GenParameter> parameters;

    GenConstructor(GenType type, Constructor<?> constructor, List<GenParameter> parameters) {
        this.type = type;
        this.constructor = constructor;
        this.parameters = parameters;
    }

    @SuppressWarnings("unchecked")
    <T> T instantiate(Object... parameters) {
        try {
            constructor.setAccessible(true);
            return (T) constructor.newInstance(parameters);
        } catch (Exception e) {
            throw new GenConstructionException("Exception occurred during '" + type + "' class instantiation due to: ", e);
        }
    }

    GenType type() {
        return type;
    }

    List<GenParameter> parameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return "[type=" + type + ']';
    }
}
