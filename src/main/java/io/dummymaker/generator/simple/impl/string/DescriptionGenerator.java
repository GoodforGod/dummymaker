package io.dummymaker.generator.simple.impl.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.PhraseBundle;
import io.dummymaker.generator.simple.IGenerator;

/**
 * Generates phrase description analog
 *
 * @author GoodforGod
 * @since 20.07.2019
 */
public class DescriptionGenerator implements IGenerator<String> {

    private final IBundle<String> bundle = new PhraseBundle();

    @Override
    public String generate() {
        return bundle.getRandom() + ", " + bundle.getRandom();
    }
}
