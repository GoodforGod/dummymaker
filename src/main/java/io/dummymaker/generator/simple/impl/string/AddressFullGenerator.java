package io.dummymaker.generator.simple.impl.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.CountryBundle;
import io.dummymaker.generator.simple.IGenerator;

/**
 * Country with address
 *
 * @author GoodforGod
 * @see AddressGenerator
 * @since 16.07.2019
 */
public class AddressFullGenerator implements IGenerator<String> {

    private final IGenerator<String> generator = new AddressGenerator();
    private final IBundle<String> countryBundle = new CountryBundle();

    @Override
    public String generate() {
        return countryBundle.getRandom() + ", " + generator.generate();
    }
}
