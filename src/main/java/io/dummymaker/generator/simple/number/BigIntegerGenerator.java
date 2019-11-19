package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.IGenerator;

import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates big integer numbers
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class BigIntegerGenerator implements IGenerator<BigInteger> {

    @Override
    public BigInteger generate() {
        return BigInteger.valueOf(ThreadLocalRandom.current().nextLong());
    }
}
