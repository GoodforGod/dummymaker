package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.Generator;
import java.util.concurrent.ThreadLocalRandom;
import org.jetbrains.annotations.NotNull;

/**
 * Generates double
 *
 * @author Anton Kurako (GoodforGod)
 * @since 26.05.2017
 */
public final class DoubleGenerator implements Generator<Double> {

    private final double from;
    private final double to;

    public DoubleGenerator(double from, double to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public @NotNull Double get() {
        return ThreadLocalRandom.current().nextDouble(from, to);
    }
}
