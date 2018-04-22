package io.dummymaker.generator.simple.impl.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.CountryPresetBundle;
import io.dummymaker.generator.simple.IGenerator;

/**
 * Generates country as a string
 *
 * @author GoodforGod (Anton Kurako)
 * @since 07.06.2017
 */
public class CountryGenerator implements IGenerator<String> {

    private final IBundle<String> bundle = new CountryPresetBundle();

    @Override
    public String generate() {
        return bundle.getRandom();
    }
}
