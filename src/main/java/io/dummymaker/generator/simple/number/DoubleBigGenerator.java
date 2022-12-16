package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.CollectionUtils;
import io.dummymaker.util.RandomUtils;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates big double from 10 to 1000000 value
 *
 * @author Anton Kurako (GoodforGod)
 * @since 26.05.2017
 */
public final class DoubleBigGenerator implements Generator<Double> {

    @Override
    public @NotNull Double get() {
        return ThreadLocalRandom.current().nextDouble() * RandomUtils.random(10, 1000000);
    }
}
