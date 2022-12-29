package io.dummymaker.factory.refactored;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Please Add Description Here.
 *
 * @author Anton Kurako (GoodforGod)
 * @since 30.11.2022
 */
final class SimpleGenType implements GenType {

    private final Class<?> value;
    private final List<GenType> generics;

    private SimpleGenType(Class<?> value, List<GenType> generics) {
        this.value = value;
        this.generics = generics;
    }

    public static GenType ofClass(Class<?> type) {
        return new SimpleGenType(type, Collections.emptyList());
    }

    public static GenType ofField(Field field) {
        return ofClass(field.getType());
    }

    @Override
    public @NotNull Class<?> value() {
        return value;
    }

    @Override
    public @NotNull List<GenType> generics() {
        return generics;
    }

    @Override
    public String toString() {
        if (generics.isEmpty()) {
            return value.getSimpleName();
        } else {
            return generics.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", ", value.getSimpleName() + "<", ">"));
        }
    }
}
