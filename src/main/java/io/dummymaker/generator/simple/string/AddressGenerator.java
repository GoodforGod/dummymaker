package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.impl.CityBundle;
import io.dummymaker.bundle.impl.DistrictBundle;
import io.dummymaker.bundle.impl.StreetBundle;
import io.dummymaker.generator.Generator;
import io.dummymaker.util.CollectionUtils;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Full address with street, city, district and house number
 *
 * @author Anton Kurako (GoodforGod)
 * @see AddressGenerator
 * @since 16.07.2019
 */
public final class AddressGenerator implements Generator<String> {

    private static final Pattern PATTERN = Pattern.compile("addr(ess)?", CASE_INSENSITIVE);

    private static final Bundle STREET_BUNDLE = new StreetBundle();
    private static final Bundle DISTRICT_BUNDLE = new DistrictBundle();
    private static final Bundle CITY_BUNDLE = new CityBundle();

    @Override
    public @NotNull String get() {
        return CITY_BUNDLE.random()
                + ", " + DISTRICT_BUNDLE.random()
                + ", " + STREET_BUNDLE.random()
                + " " + RandomUtils.random(20);
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
