package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.CollectionUtils;
import io.dummymaker.util.RandomUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Generates byte from -127 to 128
 *
 * @author Anton Kurako (GoodforGod)
 * @since 04.11.2018
 */
public final class ByteGenerator implements Generator<Byte> {

    @Override
    public @NotNull Byte get() {
        return (byte) RandomUtils.random(Byte.MIN_VALUE, Byte.MAX_VALUE);
    }
}
