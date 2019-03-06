package io.dummymaker.generator.simple.impl.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.SurnameBundle;
import io.dummymaker.generator.simple.IGenerator;

/**
 * ! NO DESCRIPTION !
 *
 * @author GoodforGod
 * @since 06.03.2019
 */
public class SurnameGenerator implements IGenerator<String> {

    private final IBundle<String> bundle = new SurnameBundle();

    @Override
    public String generate() {
        return bundle.getRandom();
    }
}
