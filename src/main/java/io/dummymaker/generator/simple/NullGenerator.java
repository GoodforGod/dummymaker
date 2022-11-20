package io.dummymaker.generator.simple;

import io.dummymaker.factory.GenStorage;
import io.dummymaker.generator.ComplexGenerator;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Generates null values
 *
 * @author Anton Kurako (GoodforGod)
 * @since 31.05.2017
 */
public final class NullGenerator implements ComplexGenerator {

    @Override
    public @Nullable Object get() {
        return null;
    }

    @Override
    public @Nullable Object generate(final @NotNull Class<?> parent,
                                     final @NotNull Field field,
                                     final @NotNull GenStorage storage,
                                     final Annotation annotation,
                                     final int depth) {
        return null;
    }
}
