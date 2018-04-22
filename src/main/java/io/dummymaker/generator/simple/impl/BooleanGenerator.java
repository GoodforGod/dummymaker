package io.dummymaker.generator.simple.impl;

import io.dummymaker.generator.simple.IGenerator;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Generates boolean values
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class BooleanGenerator implements IGenerator<Boolean> {

    @Override
    public Boolean generate() {
        return current().nextBoolean();
    }
}
