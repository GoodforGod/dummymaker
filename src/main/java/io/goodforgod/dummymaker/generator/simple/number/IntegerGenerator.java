package io.goodforgod.dummymaker.generator.simple.number;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.util.RandomUtils;
import java.util.regex.Pattern;
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

    private final int from;
    private final int to;

    public IntegerGenerator() {
        this(0, Integer.MAX_VALUE);
    }

    public IntegerGenerator(int from, int to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public @NotNull Integer get() {
        return RandomUtils.random(from, to);
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
