package io.dummymaker.generator.simple;

import io.dummymaker.annotation.special.GenEmbedded;
import io.dummymaker.generator.IGenerator;

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

        return Math.min(depth, GenEmbedded.MAX);

    }

    @Override
    public Object generate() {
        return null;
    }
}
