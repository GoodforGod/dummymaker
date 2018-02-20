package io.dummymaker.generator.impl.string;

import io.dummymaker.bundle.impl.CountryPresetBundle;
import io.dummymaker.generator.IGenerator;

/**
 * Generates country as a string
 *
 * @author GoodforGod (Anton Kurako)
 * @since 07.06.2017
 */
public class CountryGenerator implements IGenerator<String> {

    @Override
    public String generate() {
        return new CountryPresetBundle().getRandom();
    }
}
