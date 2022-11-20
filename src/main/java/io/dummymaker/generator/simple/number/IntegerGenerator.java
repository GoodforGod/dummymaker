package io.dummymaker.generator.simple.number;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.CollectionUtils;
import java.util.regex.Pattern;

import io.dummymaker.util.RandomUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Generates int from 0 to Integer.MAX_VALUE
 *
 * @author Anton Kurako (GoodforGod)
 * @see Integer#MAX_VALUE
 * @since 05.03.2019
 */
public final class IntegerGenerator implements Generator<Integer> {

    private static final Pattern PATTERN = Pattern.compile("num(ber)?|counter|series", CASE_INSENSITIVE);

    @Override
    public @NotNull Integer get() {
        return RandomUtils.random(Integer.MAX_VALUE);
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }

    @Override
    public int order() {
        return -60;
    }
}
