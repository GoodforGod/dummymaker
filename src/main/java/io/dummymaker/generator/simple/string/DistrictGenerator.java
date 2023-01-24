package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.DistrictBundle;
import io.dummymaker.generator.Localisation;
import io.dummymaker.generator.LocalizedGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates district
 *
 * @author Anton Kurako (GoodforGod)
 * @since 16.07.2019
 */
public final class DistrictGenerator implements LocalizedGenerator<String> {

    private static final Bundle BUNDLE = new DistrictBundle();
    private static final Pattern PATTERN = Pattern.compile("state|region|district|locale?", CASE_INSENSITIVE);

    @Override
    public @NotNull String get(@NotNull Localisation localisation) {
        return BUNDLE.random(localisation);
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
