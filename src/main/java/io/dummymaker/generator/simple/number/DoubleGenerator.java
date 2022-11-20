package io.dummymaker.generator.simple.number;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.Generator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates double from 0 to 1
 *
 * @author Anton Kurako (GoodforGod)
 * @since 26.05.2017
 */
public final class DoubleGenerator implements Generator<Double> {

    private static final Pattern PATTERN = Pattern.compile("probability|chance|odds|expectation|possibility", CASE_INSENSITIVE);

    @Override
    public @NotNull Double get() {
        return ThreadLocalRandom.current().nextDouble();
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
