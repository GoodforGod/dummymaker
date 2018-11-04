package io.dummymaker.generator.simple.impl.array;

import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.number.IntegerGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates array of ints
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class IntArrayGenerator implements IGenerator<Integer[]> {

    private final IntegerGenerator generator = new IntegerGenerator();

    @Override
    public Integer[] generate() {
        final int size = ThreadLocalRandom.current().nextInt(1, 20);
        final Integer[] result = new Integer[size];
        for(int i = 0; i < size; i++)
            result[i] = generator.generate();

        return result;
    }
}
