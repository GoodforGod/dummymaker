package io.dummymaker.generator.simple.impl;

import io.dummymaker.generator.simple.IGenerator;

/**
 * Generates object as string like: object1245155
 *
 * @author GoodforGod
 * @since 26.04.2018
 */
public class ObjectGenerator implements IGenerator<Object> {

    @Override
    public Object generate() {
        return new Object();
    }
}
