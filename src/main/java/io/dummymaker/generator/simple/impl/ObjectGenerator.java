package io.dummymaker.generator.simple.impl;

import io.dummymaker.generator.simple.IGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates object as string like: object_1245155
 *
 * @author GoodforGod
 * @since 26.04.2018
 */
public class ObjectGenerator implements IGenerator<Object> {

    @Override
    public Object generate() {
        return "object_" + ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
    }
}
