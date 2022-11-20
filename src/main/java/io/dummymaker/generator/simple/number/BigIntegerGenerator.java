package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.CollectionUtils;
import java.math.BigInteger;

import io.dummymaker.util.RandomUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Generates big integer numbers
 *
 * @author Anton Kurako (GoodforGod)
 * @since 04.11.2018
 */
public final class BigIntegerGenerator implements Generator<BigInteger> {

    @Override
    public @NotNull BigInteger get() {
        return BigInteger.valueOf(RandomUtils.random());
    }
}
