package io.dummymaker.generator;

import io.dummymaker.bundle.CityPresetBundle;

/**
 * Generates city in english as a string
 *
 * @author GoodforGod (Anton Kurako)
 * @since 07.06.2017
 */
public class CityGenerator implements IGenerator<String> {

    @Override
    public String generate() {
        return new CityPresetBundle().getRandom();
    }
}
