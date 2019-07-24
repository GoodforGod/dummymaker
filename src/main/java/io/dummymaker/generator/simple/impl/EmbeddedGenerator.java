package io.dummymaker.generator.simple.impl;

import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.generator.simple.IGenerator;

/**
 * Used as a marker generator for embedded annotation
 *
 * @author GoodforGod
 * @see io.dummymaker.annotation.special.GenEmbedded
 * @since 09.03.2018
 */
public class EmbeddedGenerator implements IGenerator<Object> {

    public static int toDepth(int depth) {
        if (depth < 1)
            return 1;

        if (depth > GenEmbedded.MAX)
            return GenEmbedded.MAX;

        return depth;
    }

    @Override
    public Object generate() {
        return null;
    }
}
