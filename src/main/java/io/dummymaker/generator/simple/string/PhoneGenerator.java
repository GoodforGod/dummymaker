package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.CollectionUtils;
import java.util.regex.Pattern;

import io.dummymaker.util.RandomUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Generates simple mobile phone as a string
 *
 * @author Anton Kurako (GoodforGod)
 * @since 26.05.2017
 */
public final class PhoneGenerator implements Generator<String> {

    private static final Pattern PATTERN = Pattern.compile("telefax|phone|mobile(phone)?|dial", CASE_INSENSITIVE);

    @Override
    public @NotNull String get() {
        return RandomUtils.random(1, 9)
                + "("
                + RandomUtils.random(100, 999)
                + ")"
                + RandomUtils.random(100, 999)
                + RandomUtils.random(1000, 9999);
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }

    @Override
    public int order() {
        return -65;
    }
}
