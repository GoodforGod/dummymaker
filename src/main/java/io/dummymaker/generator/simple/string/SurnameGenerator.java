package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.impl.SurnameBundle;
import io.dummymaker.generator.Generator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Surname generator
 *
 * @author Anton Kurako (GoodforGod)
 * @since 06.03.2019
 */
public final class SurnameGenerator implements Generator<String> {

    private static final Pattern PATTERN = Pattern.compile("surname", CASE_INSENSITIVE);

    private static final Bundle BUNDLE = new SurnameBundle();

    @Override
    public @NotNull String get() {
        return BUNDLE.random();
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }

    @Override
    public int order() {
        return -10;
    }
}
