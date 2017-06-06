package io.dummymaker.generator;

import io.dummymaker.bundle.PhrasePresetBundle;

/**
 * Generates some english phrase as a string
 *
 * @author GoodforGod (Anton Kurako)
 * @since 07.06.2017
 */
public class PhraseGenerator implements IGenerator<String> {

    @Override
    public String generate() {
        return new PhrasePresetBundle().getRandom();
    }
}
