package io.dummymaker.generator.impl;

import io.dummymaker.generator.IGenerator;

import java.util.Objects;

/**
 * Used as a marker generator for collection generators or other generate factories
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

    public Objects generate(final Class<?> tClass) {

        return null;
    }
}
