package io.dummymaker.generator.impl;

import io.dummymaker.generator.IGenerator;

/**
 * Used as a marker generator for collection generators or other generate factories
 *
 * @author GoodforGod
 * @since 09.03.2018
 */
public class InlineGenerator implements IGenerator<Object> {

    @Override
    public Object generate() {
        return null;
    }
}
