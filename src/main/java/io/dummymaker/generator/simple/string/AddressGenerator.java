package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.CityBundle;
import io.dummymaker.bundle.DistrictBundle;
import io.dummymaker.bundle.StreetBundle;
import io.dummymaker.generator.GenParameters;
import io.dummymaker.generator.Localisation;
import io.dummymaker.generator.ParameterizedGenerator;
import io.dummymaker.util.RandomUtils;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Full address with street, city, district and house number
 *
 * @author Anton Kurako (GoodforGod)
 * @see AddressGenerator
 * @since 16.07.2019
 */
public final class AddressGenerator implements ParameterizedGenerator<CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("addr(ess)?", CASE_INSENSITIVE);

    private static final Bundle STREET_BUNDLE = new StreetBundle();
    private static final Bundle DISTRICT_BUNDLE = new DistrictBundle();
    private static final Bundle CITY_BUNDLE = new CityBundle();

    @Override
    public @NotNull String get(@NotNull GenParameters parameters) {
        return parameters.namingCase().apply(getValue(parameters.localisation())).toString();
    }

    @Override
    public String get() {
        return getValue(Localisation.ENGLISH);
    }

    private static String getValue(Localisation localisation) {
        return CITY_BUNDLE.random(localisation)
                + ", " + DISTRICT_BUNDLE.random(localisation)
                + ", " + STREET_BUNDLE.random(localisation)
                + " " + RandomUtils.random(20);
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
