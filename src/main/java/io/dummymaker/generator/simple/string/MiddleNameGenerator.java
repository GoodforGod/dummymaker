package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.impl.MiddleNameBundle;
import io.dummymaker.generator.Generator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Middle name generator
 *
 * @author Anton Kurako (GoodforGod)
 * @since 16.07.2019
 */
public final class MiddleNameGenerator implements Generator<String> {

    private static final Pattern PATTERN = Pattern.compile("middle(name)?|patronymic", CASE_INSENSITIVE);
    private static final Bundle BUNDLE = new MiddleNameBundle();

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
        return -60;
    }
}
