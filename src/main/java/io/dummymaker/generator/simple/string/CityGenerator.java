package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.impl.CityBundle;
import io.dummymaker.generator.Generator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates city in english as a string
 *
 * @author Anton Kurako (GoodforGod) (Anton Kurako)
 * @since 07.06.2017
 */
public final class CityGenerator implements Generator<String> {

    private static final Bundle BUNDLE = new CityBundle();
    private static final Pattern PATTERN = Pattern.compile("city|town|village|settlement", CASE_INSENSITIVE);

    @Override
    public @NotNull String get() {
        return BUNDLE.random();
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
