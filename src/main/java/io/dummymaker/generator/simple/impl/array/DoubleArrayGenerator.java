package io.dummymaker.generator.simple.impl.array;

import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.number.ByteGenerator;
import io.dummymaker.generator.simple.impl.number.DoubleBigGenerator;

/**
 * Generates array of doubles
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class DoubleArrayGenerator implements IGenerator<Double[]> {

    private final DoubleBigGenerator generator = new DoubleBigGenerator();
    private final ByteGenerator arrayGenerator = new ByteGenerator();

    @Override
    public Double[] generate() {
        final int size = arrayGenerator.generate();
        final Double[] result = new Double[size];
        for(int i = 0; i < size; i++)
            result[i] = generator.generate();

        return result;
    }
}
