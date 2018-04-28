package io.dummymaker.generator.simple.impl.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.PhrasePresetBundle;
import io.dummymaker.generator.simple.IGenerator;

/**
 * Generates some english phrase as a string
 *
 * @author GoodforGod (Anton Kurako)
 * @since 07.06.2017
 */
public class PhraseGenerator implements IGenerator<String> {

    private final IBundle<String> bundle = new PhrasePresetBundle();

    @Override
    public String generate() {
        return bundle.getRandom();
    }
}
