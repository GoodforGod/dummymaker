package io.dummymaker.generator.simple.impl.array;

import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.number.FloatBigGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates array of floats
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class FloatArrayGenerator implements IGenerator<Float[]> {

    private final FloatBigGenerator generator = new FloatBigGenerator();

    @Override
    public Float[] generate() {
        final int size = ThreadLocalRandom.current().nextInt(1, 20);
        final Float[] result = new Float[size];
        for(int i = 0; i < size; i++)
            result[i] = generator.generate();

        return result;
    }
}
