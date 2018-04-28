package io.dummymaker.generator.simple.impl.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.CityPresetBundle;
import io.dummymaker.generator.simple.IGenerator;

/**
 * Generates city in english as a string
 *
 * @author GoodforGod (Anton Kurako)
 * @since 07.06.2017
 */
public class CityGenerator implements IGenerator<String> {

    private final IBundle<String> bundle = new CityPresetBundle();

    @Override
    public String generate() {
        return bundle.getRandom();
    }
}
