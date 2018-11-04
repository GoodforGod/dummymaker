package io.dummymaker.generator.simple.impl.array;

import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.number.ByteGenerator;
import io.dummymaker.generator.simple.impl.number.ShortGenerator;

/**
 * Generates array of shorts
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class ShortArrayGenerator implements IGenerator<Short[]> {

    private final ShortGenerator generator = new ShortGenerator();
    private final ByteGenerator arrayGenerator = new ByteGenerator();

    @Override
    public Short[] generate() {
        final int size = arrayGenerator.generate();
        final Short[] result = new Short[size];
        for(int i = 0; i < size; i++)
            result[i] = generator.generate();

        return result;
    }
}
