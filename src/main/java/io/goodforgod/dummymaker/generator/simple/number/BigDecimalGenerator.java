package io.goodforgod.dummymaker.generator.simple.number;

import static java.util.concurrent.ThreadLocalRandom.current;

import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.util.RandomUtils;
import java.math.BigDecimal;
import org.jetbrains.annotations.NotNull;

/**
 * Generates big decimal numbers
 *
 * @author Anton Kurako (GoodforGod)
 * @since 04.11.2018
 */
public final class BigDecimalGenerator implements Generator<BigDecimal> {

    @Override
    public @NotNull BigDecimal get() {
        final BigDecimal decimal = BigDecimal.valueOf(RandomUtils.random(0, Long.MAX_VALUE));
        return decimal.add(BigDecimal.valueOf(current().nextDouble(0.0001, 0.9999)));
    }
}
