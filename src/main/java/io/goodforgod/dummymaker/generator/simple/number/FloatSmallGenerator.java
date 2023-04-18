package io.goodforgod.dummymaker.generator.simple.number;

import io.goodforgod.dummymaker.generator.Generator;
import java.util.concurrent.ThreadLocalRandom;
import org.jetbrains.annotations.NotNull;

/**
 * Generates float number from 0.0 to 1.0
 *
 * @author Anton Kurako (GoodforGod)
 * @since 04.11.2018
 */
public final class FloatSmallGenerator implements Generator<Float> {

    @Override
    public @NotNull Float get() {
        return ThreadLocalRandom.current().nextFloat();
    }
}
