package io.dummymaker.generator.simple.impl.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.TagPresetBundle;
import io.dummymaker.generator.simple.IGenerator;

/**
 * Generates tag as a string like #tag
 *
 * @author GoodforGod (Anton Kurako)
 * @since 07.06.2017
 */
public class TagGenerator implements IGenerator<String> {

    private final IBundle<String> bundle = new TagPresetBundle();

    @Override
    public String generate() {
        return bundle.getRandom();
    }
}
