package io.goodforgod.dummymaker.generator.simple.number;

import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.util.RandomUtils;
import java.math.BigInteger;
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
