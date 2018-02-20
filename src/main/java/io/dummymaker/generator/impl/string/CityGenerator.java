package io.dummymaker.generator.impl.string;

import io.dummymaker.bundle.impl.CityPresetBundle;
import io.dummymaker.generator.IGenerator;

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
