package io.dummymaker.generator.simple.impl.number;

import io.dummymaker.generator.simple.IGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates long values
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class LongGenerator implements IGenerator<Long> {

    @Override
    public Long generate() {
        return ThreadLocalRandom.current().nextLong();
    }
}
