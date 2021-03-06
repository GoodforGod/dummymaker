package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;

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
    public @NotNull BigDecimal generate() {
        final BigDecimal decimal = BigDecimal.valueOf(CollectionUtils.random(Long.MIN_VALUE, Long.MAX_VALUE));
        return decimal.add(BigDecimal.valueOf(current().nextDouble(0.0001, 0.9999)));
    }
}
