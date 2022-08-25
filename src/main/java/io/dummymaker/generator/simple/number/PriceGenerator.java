package io.dummymaker.generator.simple.number;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;
import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 25.08.2022
 */
public class PriceGenerator implements IGenerator<BigDecimal> {

    private final Pattern pattern = Pattern.compile("price|money|cash|bucks|bills", CASE_INSENSITIVE);

    @Override
    public BigDecimal generate() {
        final int preDot = CollectionUtils.random(1, 999999);
        final int afterDot = CollectionUtils.random(1, 99);
        return new BigDecimal(preDot + "." + afterDot);
    }

    @Override
    public Pattern pattern() {
        return pattern;
    }

    @Override
    public int order() {
        return -40;
    }
}
