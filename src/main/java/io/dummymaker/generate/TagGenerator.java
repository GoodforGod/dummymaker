package io.dummymaker.generate;

import io.dummymaker.bundle.TagPresetBundle;

/**
 * Generates tag as a string like #tag
 *
 * @author GoodforGod (Anton Kurako)
 * @since 07.06.2017
 */
public class TagGenerator implements IGenerator<String> {

    @Override
    public String generate() {
        return new TagPresetBundle().getRandom();
    }
}
