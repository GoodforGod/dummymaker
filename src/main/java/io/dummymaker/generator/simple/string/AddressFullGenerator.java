package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.CountryBundle;
import io.dummymaker.generator.Generator;
import io.dummymaker.generator.Localisation;
import io.dummymaker.generator.LocalizedGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Country with address
 *
 * @author Anton Kurako (GoodforGod)
 * @see AddressGenerator
 * @since 16.07.2019
 */
public final class AddressFullGenerator implements LocalizedGenerator<String> {

    private static final Pattern PATTERN = Pattern.compile("addr(ess)?full|place|residence|home|location", CASE_INSENSITIVE);

    private static final Generator<String> GENERATOR = new AddressGenerator();
    private static final Bundle COUNTRY_BUNDLE = new CountryBundle();

    @Override
    public @NotNull String get(@NotNull Localisation localisation) {
        return COUNTRY_BUNDLE.random(localisation) + ", " + GENERATOR.get();
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
