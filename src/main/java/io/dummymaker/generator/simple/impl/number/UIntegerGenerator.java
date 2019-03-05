package io.dummymaker.generator.simple.impl.number;

import io.dummymaker.generator.simple.IGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates int from 0 to Integer.MAX_VALUE
 *
 * @see Integer#MAX_VALUE
 *
 * @author GoodforGod
 * @since 05.03.2019
 */
public class UIntegerGenerator implements IGenerator<Integer> {

    @Override
    public Integer generate() {
        return ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
    }
}
