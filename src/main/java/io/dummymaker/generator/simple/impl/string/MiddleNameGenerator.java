package io.dummymaker.generator.simple.impl.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.MiddleNameBundle;
import io.dummymaker.generator.simple.IGenerator;

/**
 * Middle name generator
 *
 * @author GoodforGod
 * @since 16.07.2019
 */
public class MiddleNameGenerator implements IGenerator<String> {

    private final IBundle<String> bundle = new MiddleNameBundle();

    @Override
    public String generate() {
        return bundle.getRandom();
    }
}
