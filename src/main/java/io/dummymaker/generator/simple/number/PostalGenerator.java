package io.dummymaker.generator.simple.number;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.CollectionUtils;
import java.util.regex.Pattern;

import io.dummymaker.util.RandomUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Generates medium integer values between 100000 and 999999
 *
 * @author Anton Kurako (GoodforGod)
 * @since 12.10.2019
 */
public final class PostalGenerator implements Generator<Integer> {

    private static final Pattern PATTERN = Pattern.compile("number|postal|code|index|zip(code)?", CASE_INSENSITIVE);

    @Override
    public @NotNull Integer get() {
        return RandomUtils.random(100000, 999999);
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
