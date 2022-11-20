package io.dummymaker.generator.simple.number;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.CollectionUtils;
import java.util.regex.Pattern;

import io.dummymaker.util.RandomUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Generates small integer numbers from 1 up to 100
 *
 * @author Anton Kurako (GoodforGod)
 * @since 25.07.2019
 */
public final class IntegerSmallGenerator implements Generator<Integer> {

    private static final Pattern PATTERN = Pattern.compile("rank|age|grade|group|amount|inn|snils", CASE_INSENSITIVE);

    @Override
    public @NotNull Integer get() {
        return RandomUtils.random(1, 99);
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }

    @Override
    public int order() {
        return -50;
    }
}
