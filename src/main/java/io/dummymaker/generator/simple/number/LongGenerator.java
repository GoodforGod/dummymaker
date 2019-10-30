package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.IGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Long positive number generator
 *
 * @author GoodforGod
 * @see Long#MAX_VALUE
 * @since 15.09.2019
 */
public class LongGenerator implements IGenerator<Long> {

    @Override
    public Long generate() {
        return ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
    }
}
