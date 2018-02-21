package io.dummymaker.generator.impl;

import io.dummymaker.generator.IGenerator;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * "Default Description"
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
