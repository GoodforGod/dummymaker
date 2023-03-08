package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.*;
import io.dummymaker.generator.*;
import io.dummymaker.util.RandomUtils;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Country with address
 *
 * @author Anton Kurako (GoodforGod)
 * @see AddressGenerator
 * @since 16.07.2019
 */
public final class AddressFullGenerator implements ParameterizedGenerator<CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("addr(ess)?full|place|residence|home|location", CASE_INSENSITIVE);

    private static final Bundle STREET_BUNDLE = new StreetBundle();
    private static final Bundle DISTRICT_BUNDLE = new DistrictBundle();
    private static final Bundle CITY_BUNDLE = new CityBundle();
    private static final Bundle COUNTRY_BUNDLE = new CountryBundle();

    @Override
    public @NotNull String get(@NotNull GenParameters parameters) {
        return parameters.namingCase().apply(get(parameters.localisation())).toString();
    }

    @Override
    public String get() {
        return get(Localisation.ENGLISH);
    }

    private static String get(Localisation localisation) {
        return COUNTRY_BUNDLE.random(localisation)
                + ", " + CITY_BUNDLE.random(localisation)
                + ", " + DISTRICT_BUNDLE.random(localisation)
                + ", " + STREET_BUNDLE.random(localisation)
                + " " + RandomUtils.random(20);
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
