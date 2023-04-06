package io.goodforgod.dummymaker.generator.simple.number;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.util.RandomUtils;
import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 25.08.2022
 */
public final class PriceGenerator implements Generator<BigDecimal> {

    private static final Pattern PATTERN = Pattern.compile("price|cost|fee|bills?|balance|money|cash|bucks|funds?",
            CASE_INSENSITIVE);

    @Override
    public BigDecimal get() {
        final BigDecimal preDot = BigDecimal.valueOf(RandomUtils.random(1, 9999));
        final double afterDotDouble = ((double) RandomUtils.random(0, 99) / 100);
        final BigDecimal afterDot = BigDecimal.valueOf(afterDotDouble);
        return preDot.add(afterDot);
    }

    @Override
    public Pattern pattern() {
        return PATTERN;
    }

    @Override
    public int order() {
        return -40;
    }
}
