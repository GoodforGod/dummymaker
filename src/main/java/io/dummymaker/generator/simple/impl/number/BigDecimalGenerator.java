package io.dummymaker.generator.simple.impl.number;

import io.dummymaker.generator.simple.IGenerator;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates big decimal numbers
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class BigDecimalGenerator implements IGenerator<BigDecimal> {

    @Override
    public BigDecimal generate() {
        return BigDecimal.valueOf(ThreadLocalRandom.current().nextLong());
    }
}
