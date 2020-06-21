package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

/**
 * Generates big integer numbers
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class BigIntegerGenerator implements IGenerator<BigInteger> {

    @Override
    public @NotNull BigInteger generate() {
        return BigInteger.valueOf(CollectionUtils.random());
    }
}
