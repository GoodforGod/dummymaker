package io.dummymaker.generator.simple.impl;

import io.dummymaker.generator.simple.IGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates boolean values
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class BooleanGenerator implements IGenerator<Boolean> {

    @Override
    public Boolean generate() {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
