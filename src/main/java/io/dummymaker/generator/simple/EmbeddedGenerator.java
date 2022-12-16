package io.dummymaker.generator.simple;

import io.dummymaker.annotation.GenAuto;
import io.dummymaker.annotation.GenDepth;
import io.dummymaker.generator.Generator;
import org.jetbrains.annotations.Nullable;

/**
 * Used as a marker generator for embedded annotation
 *
 * @author Anton Kurako (GoodforGod)
 * @see GenAuto
 * @since 09.03.2018
 */
public final class EmbeddedGenerator implements Generator<Object> {

    public static int toDepth(int depth) {
        return (depth < 1)
                ? 1
                : Math.min(depth, GenDepth.MAX);
    }

    @Override
    public @Nullable Object get() {
        return null;
    }
}
