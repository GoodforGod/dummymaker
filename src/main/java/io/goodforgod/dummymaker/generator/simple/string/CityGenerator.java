package io.goodforgod.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.bundle.Bundle;
import io.goodforgod.dummymaker.bundle.CityBundle;
import io.goodforgod.dummymaker.generator.GenParameters;
import io.goodforgod.dummymaker.generator.Localisation;
import io.goodforgod.dummymaker.generator.ParameterizedGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates city in english as a string
 *
 * @author Anton Kurako (GoodforGod) (Anton Kurako)
 * @since 07.06.2017
 */
public final class CityGenerator implements ParameterizedGenerator<CharSequence> {

    private static final Bundle BUNDLE = new CityBundle();
    private static final Pattern PATTERN = Pattern.compile("city|town|village|settlement", CASE_INSENSITIVE);

    @Override
    public @NotNull String get(@NotNull GenParameters parameters) {
        return parameters.namingCase().apply(getValue(parameters.localisation())).toString();
    }

    @Override
    public String get() {
        return getValue(Localisation.ENGLISH);
    }

    private static String getValue(Localisation localisation) {
        return BUNDLE.random(localisation);
    }

    @Override
    public @NotNull Hints hints() {
        return Hints.builder()
                .withPattern(PATTERN)
                .build();
    }
}
