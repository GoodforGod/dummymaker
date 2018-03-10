package io.dummymaker.generator.impl.number;

import io.dummymaker.generator.IGenerator;

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
