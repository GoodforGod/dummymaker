package io.dummymaker.generator.simple.impl.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.CityBundle;
import io.dummymaker.bundle.impl.DistrictBundle;
import io.dummymaker.bundle.impl.StreetBundle;
import io.dummymaker.generator.simple.IGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Full address with street, city, district and house number
 *
 * @see AddressGenerator
 *
 * @author GoodforGod
 * @since 16.07.2019
 */
public class AddressGenerator implements IGenerator<String> {

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
}
