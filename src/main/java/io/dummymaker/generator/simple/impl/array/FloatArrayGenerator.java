package io.dummymaker.generator.simple.impl.array;

import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.number.ByteGenerator;
import io.dummymaker.generator.simple.impl.number.FloatBigGenerator;

/**
 * Generates array of floats
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class FloatArrayGenerator implements IGenerator<Float[]> {

    private final FloatBigGenerator generator = new FloatBigGenerator();
    private final ByteGenerator arrayGenerator = new ByteGenerator();

    @Override
    public Float[] generate() {
        final int size = arrayGenerator.generate();
        final Float[] result = new Float[size];
        for(int i = 0; i < size; i++)
            result[i] = generator.generate();

        return result;
    }
}
