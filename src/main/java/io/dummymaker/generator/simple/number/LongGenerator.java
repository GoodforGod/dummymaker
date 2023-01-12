package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.RandomUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Long positive number generator
 *
 * @author Anton Kurako (GoodforGod)
 * @see Long#MAX_VALUE
 * @since 15.09.2019
 */
public final class LongGenerator implements Generator<Long> {

    @Override
    public @NotNull Long get() {
        return RandomUtils.random(Long.MAX_VALUE);
    }
}
