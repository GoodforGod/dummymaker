package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.RandomUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Generates short from 0 to Short.MAX_VALUE
 *
 * @author Anton Kurako (GoodforGod)
 * @see Short#MAX_VALUE
 * @since 05.03.2019
 */
public final class ShortGenerator implements Generator<Short> {

    private final short from;
    private final short to;

    public ShortGenerator() {
        this((short) 0, Short.MAX_VALUE);
    }

    public ShortGenerator(short from, short to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public @NotNull Short get() {
        return ((short) RandomUtils.random(from, to));
    }
}
