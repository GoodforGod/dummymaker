package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.impl.FormatBundle;
import io.dummymaker.generator.Generator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates programming format name
 *
 * @author Anton Kurako (GoodforGod)
 * @since 4.5.2020
 */
public final class FormatGenerator implements Generator<String> {

    private static final Bundle FORMATS = new FormatBundle();
    private static final Pattern PATTERN = Pattern.compile("protocol|format", CASE_INSENSITIVE);

    @Override
    public @NotNull String get() {
        return FORMATS.random();
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
