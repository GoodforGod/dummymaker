package io.dummymaker.generator.simple.impl.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.StreetBundle;
import io.dummymaker.generator.simple.IGenerator;

/**
 * Street name generator
 *
 * @author GoodforGod
 * @since 16.07.2019
 */
public class StreetGenerator implements IGenerator<String> {

    private final IBundle<String> bundle = new StreetBundle();

    @Override
    public String generate() {
        return bundle.getRandom();
    }
}
