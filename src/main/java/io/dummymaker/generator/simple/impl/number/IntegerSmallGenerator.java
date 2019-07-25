package io.dummymaker.generator.simple.impl.number;

import io.dummymaker.generator.simple.IGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates small integer numbers from 1 up to 100
 *
 * @author GoodforGod
 * @since 25.07.2019
 */
public class IntegerSmallGenerator implements IGenerator<Integer> {

    @Override
    public Integer generate() {
        return ThreadLocalRandom.current().nextInt(1, 101);
    }
}
