package io.dummymaker.generator.simple;

import io.dummymaker.factory.IGenStorage;
import io.dummymaker.generator.IComplexGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Generates null values
 *
 * @author GoodforGod
 * @since 31.05.2017
 */
public class NullGenerator implements IComplexGenerator {

    @Override
    public @Nullable Object generate() {
        return null;
    }

    @Override
    public @Nullable Object generate(final @NotNull Class<?> parent,
                                     final @NotNull Field field,
                                     final @NotNull IGenStorage storage,
                                     final Annotation annotation,
                                     final int depth) {
        return null;
    }
}
