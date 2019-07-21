package io.dummymaker.generator.simple.impl.number;

import io.dummymaker.generator.simple.IGenerator;

import java.math.BigDecimal;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Generates big decimal numbers
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class BigDecimalGenerator implements IGenerator<BigDecimal> {

    @Override
    public BigDecimal generate() {
        final BigDecimal decimal = BigDecimal.valueOf(current().nextLong(Long.MIN_VALUE, Long.MAX_VALUE));
        return decimal.add(BigDecimal.valueOf(current().nextDouble(0.0001, 0.9999)));
    }
}
