package io.dummymaker.generator.simple.number;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.RandomUtils;
import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 25.08.2022
 */
public final class PriceGenerator implements Generator<BigDecimal> {

    private static final Pattern PATTERN = Pattern.compile("price|money|cash|bucks|bills", CASE_INSENSITIVE);

    @Override
    public BigDecimal get() {
        final int preDot = RandomUtils.random(1, 999999);
        final int afterDot = RandomUtils.random(1, 99);
        return new BigDecimal(preDot + "." + afterDot);
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
