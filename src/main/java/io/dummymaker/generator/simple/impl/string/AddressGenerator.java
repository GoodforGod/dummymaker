package io.dummymaker.generator.simple.impl.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.CityBundle;
import io.dummymaker.bundle.impl.DistrictBundle;
import io.dummymaker.bundle.impl.StreetBundle;
import io.dummymaker.generator.simple.IGenerator;

import java.util.concurrent.ThreadLocalRandom;
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
    public String generate() {
        return cityBundle.getRandom()
                + ", " + districtBundle.getRandom()
                + ", " + streetBundle.getRandom()
                + " " + ThreadLocalRandom.current().nextInt(20);
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
