package io.goodforgod.dummymaker.generator.simple;

import io.goodforgod.dummymaker.annotation.GenAuto;
import io.goodforgod.dummymaker.generator.Generator;
import org.jetbrains.annotations.Nullable;

/**
 * Used as a marker generator for embedded annotation
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenAuto
 * @since 09.03.2018
 */
public final class EmbeddedGenerator implements Generator<Object> {

    @Override
    public @Nullable Object get() {
        return null;
    }
}
