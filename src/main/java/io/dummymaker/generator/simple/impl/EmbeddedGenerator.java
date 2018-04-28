package io.dummymaker.generator.simple.impl;

import io.dummymaker.generator.simple.IGenerator;

/**
 * Used as a marker generator for embedded annotation
 *
 * @see io.dummymaker.annotation.special.GenEmbedded
 *
 * @author GoodforGod
 * @since 09.03.2018
 */
public class EmbeddedGenerator implements IGenerator<Object> {

    @Override
    public Object generate() {
        return null;
    }
}
