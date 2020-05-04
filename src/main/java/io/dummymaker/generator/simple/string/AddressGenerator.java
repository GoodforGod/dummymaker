package io.dummymaker.generator.simple.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.CityBundle;
import io.dummymaker.bundle.impl.DistrictBundle;
import io.dummymaker.bundle.impl.StreetBundle;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Full address with street, city, district and house number
 *
 * @author GoodforGod
 * @see AddressGenerator
 * @since 16.07.2019
 */
public class AddressGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("addr(ess)?", CASE_INSENSITIVE);

    private final IBundle<String> streetBundle = new StreetBundle();
    private final IBundle<String> districtBundle = new DistrictBundle();
    private final IBundle<String> cityBundle = new CityBundle();

    @Override
    public @NotNull String generate() {
        return cityBundle.getRandom()
                + ", " + districtBundle.getRandom()
                + ", " + streetBundle.getRandom()
                + " " + CollectionUtils.random(20);
    }

    @Override
    public @NotNull Pattern getPattern() {
        return pattern;
    }
}
