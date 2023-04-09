package io.goodforgod.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.bundle.Bundle;
import io.goodforgod.dummymaker.bundle.DistrictBundle;
import io.goodforgod.dummymaker.generator.GenParameters;
import io.goodforgod.dummymaker.generator.Localisation;
import io.goodforgod.dummymaker.generator.ParameterizedGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates district
 *
 * @author Anton Kurako (GoodforGod)
 * @since 16.07.2019
 */
public final class DistrictGenerator implements ParameterizedGenerator<CharSequence> {

    private static final Bundle BUNDLE = new DistrictBundle();
    private static final Pattern PATTERN = Pattern.compile("state|region|district|locale?", CASE_INSENSITIVE);

    @Override
    public CharSequence get(@NotNull GenParameters parameters) {
        return parameters.namingCase().apply(get(parameters.localisation())).toString();
    }

    @Override
    public CharSequence get() {
        return get(Localisation.ENGLISH);
    }

    private static String get(@NotNull Localisation localisation) {
        return BUNDLE.random(localisation);
    }

    @Override
    public @NotNull Hints hints() {
        return Hints.builder()
                .withPattern(PATTERN)
                .build();
    }
}
