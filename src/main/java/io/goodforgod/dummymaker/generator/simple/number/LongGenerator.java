package io.goodforgod.dummymaker.generator.simple.number;

import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.util.RandomUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Long positive number generator
 *
 * @author Anton Kurako (GoodforGod)
 * @see Long#MAX_VALUE
 * @since 15.09.2019
 */
public final class LongGenerator implements Generator<Long> {

    private final long from;
    private final long to;

    public LongGenerator() {
        this(0, Long.MAX_VALUE);
    }

    public LongGenerator(long from, long to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public @NotNull Long get() {
        return RandomUtils.random(from, to);
    }
}
