package io.goodforgod.dummymaker.generator.simple.number;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.generator.Generator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates big double from 0.0 to 1.0 value
 *
 * @author Anton Kurako (GoodforGod)
 * @since 26.05.2017
 */
public final class DoubleSmallGenerator implements Generator<Double> {

    private static final Pattern PATTERN = Pattern.compile("probability|chance|odds|expectation|possibility", CASE_INSENSITIVE);

    @Override
    public @NotNull Double get() {
        return ThreadLocalRandom.current().nextDouble();
    }

    @Override
    public @NotNull Hints hints() {
        return Hints.builder()
                .withPattern(PATTERN)
                .withPriority(40)
                .build();
    }
}
