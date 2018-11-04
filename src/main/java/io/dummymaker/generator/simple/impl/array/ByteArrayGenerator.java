package io.dummymaker.generator.simple.impl.array;

import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.number.ByteGenerator;

/**
 * Generates array of bytes
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class ByteArrayGenerator implements IGenerator<Byte[]> {

    private final ByteGenerator generator = new ByteGenerator();

    @Override
    public Byte[] generate() {
        final int size = generator.generate();
        final Byte[] result = new Byte[size];
        for(int i = 0; i < size; i++)
            result[i] = generator.generate();

        return result;
    }
}
