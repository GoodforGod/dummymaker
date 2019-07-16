package io.dummymaker.generator.simple.impl.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.DistrictBundle;
import io.dummymaker.generator.simple.IGenerator;

/**
 * Generates district
 *
 * @author GoodforGod
 * @since 16.07.2019
 */
public class DistrictGenerator implements IGenerator<String> {

    private final IBundle<String> bundle = new DistrictBundle();

    @Override
    public String generate() {
        return bundle.getRandom();
    }
}
