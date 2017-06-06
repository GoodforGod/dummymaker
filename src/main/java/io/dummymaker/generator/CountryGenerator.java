package io.dummymaker.generator;

import io.dummymaker.bundle.CountryPresetBundle;

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
