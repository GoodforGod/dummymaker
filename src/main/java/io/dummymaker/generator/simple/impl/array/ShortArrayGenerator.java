package io.dummymaker.generator.simple.impl.array;

import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.number.ShortGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates array of shorts
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class ShortArrayGenerator implements IGenerator<Short[]> {

    private final ShortGenerator generator = new ShortGenerator();

    @Override
    public Short[] generate() {
        final int size = ThreadLocalRandom.current().nextInt(1, 20);
        final Short[] result = new Short[size];
        for(int i = 0; i < size; i++)
            result[i] = generator.generate();

        return result;
    }
}
