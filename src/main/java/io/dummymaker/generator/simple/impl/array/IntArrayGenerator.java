package io.dummymaker.generator.simple.impl.array;

import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.number.ByteGenerator;
import io.dummymaker.generator.simple.impl.number.IntegerGenerator;

/**
 * Generates array of ints
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class IntArrayGenerator implements IGenerator<Integer[]> {

    private final IntegerGenerator generator = new IntegerGenerator();
    private final ByteGenerator arrayGenerator = new ByteGenerator();

    @Override
    public Integer[] generate() {
        final int size = arrayGenerator.generate();
        final Integer[] result = new Integer[size];
        for(int i = 0; i < size; i++)
            result[i] = generator.generate();

        return result;
    }
}
