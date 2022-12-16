package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.CollectionUtils;
import io.dummymaker.util.RandomUtils;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates float number from 0 to 10000
 *
 * @author Anton Kurako (GoodforGod)
 * @since 04.11.2018
 */
public final class FloatBigGenerator implements Generator<Float> {

    @Override
    public @NotNull Float get() {
        return ThreadLocalRandom.current().nextFloat() * RandomUtils.random(10, 10000);
    }
}
