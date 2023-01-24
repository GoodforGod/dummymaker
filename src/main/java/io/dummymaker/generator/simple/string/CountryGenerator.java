package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.CountryBundle;
import io.dummymaker.generator.Localisation;
import io.dummymaker.generator.LocalizedGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates country as a string
 *
 * @author Anton Kurako (GoodforGod) (Anton Kurako)
 * @since 07.06.2017
 */
public final class CountryGenerator implements LocalizedGenerator<String> {

    private static final Bundle BUNDLE = new CountryBundle();
    private static final Pattern PATTERN = Pattern.compile("country|citizen", CASE_INSENSITIVE);

    @Override
    public @NotNull String get(@NotNull Localisation localisation) {
        return BUNDLE.random(localisation);
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
