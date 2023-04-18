package io.goodforgod.dummymaker.generator.simple;

import io.goodforgod.dummymaker.generator.Generator;
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
