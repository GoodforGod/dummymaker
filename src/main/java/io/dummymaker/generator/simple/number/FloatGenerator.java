package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.Generator;
import java.util.concurrent.ThreadLocalRandom;
import org.jetbrains.annotations.NotNull;

/**
 * Generates float
 *
 * @author Anton Kurako (GoodforGod)
 * @since 04.11.2018
 */
public final class FloatGenerator implements Generator<Float> {

    private final float from;
    private final float to;

    public FloatGenerator(float from, float to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public @NotNull Float get() {
        return ThreadLocalRandom.current().nextFloat(from, to);
    }
}
