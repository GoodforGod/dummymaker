package io.dummymaker.generator.simple;

import io.dummymaker.factory.GenStorage;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.complex.ComplexGenerator;
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
public final class NullGenerator implements Generator<Object> {

    @Override
    public @Nullable Object get() {
        return null;
    }
}
