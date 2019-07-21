package io.dummymaker.generator.simple.impl;

import io.dummymaker.generator.simple.IGenerator;

/**
 * Used as a marker generator for embedded annotation
 *
 * @author GoodforGod
 * @see io.dummymaker.annotation.special.GenEmbedded
 * @since 09.03.2018
 */
public class EmbeddedGenerator implements IGenerator<Object> {

    @Override
    public Object generate() {
        return null;
    }
}
