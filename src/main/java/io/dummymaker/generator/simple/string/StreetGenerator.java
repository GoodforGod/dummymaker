package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.StreetBundle;
import io.dummymaker.generator.Generator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Street name generator
 *
 * @author Anton Kurako (GoodforGod)
 * @since 16.07.2019
 */
public final class StreetGenerator implements Generator<String> {

    private static final Pattern PATTERN = Pattern.compile("street", CASE_INSENSITIVE);

    private static final Bundle BUNDLE = new StreetBundle();

    @Override
    public @NotNull String get() {
        return BUNDLE.random();
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
