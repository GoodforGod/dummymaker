package io.dummymaker.generator.simple.impl.array;

import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.number.ByteGenerator;
import io.dummymaker.generator.simple.impl.number.LongGenerator;

/**
 * Generates array of longs
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class LongArrayGenerator implements IGenerator<Long[]> {

    private final LongGenerator generator = new LongGenerator();
    private final ByteGenerator arrayGenerator = new ByteGenerator();

    @Override
    public Long[] generate() {
        final int size = arrayGenerator.generate();
        final Long[] result = new Long[size];
        for(int i = 0; i < size; i++)
            result[i] = generator.generate();

        return result;
    }
}
