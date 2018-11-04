package io.dummymaker.generator.simple.impl.array;

import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.number.ByteGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates array of bytes
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class ByteArrayGenerator implements IGenerator<byte[]> {

    private final ByteGenerator generator = new ByteGenerator();

    @Override
    public byte[] generate() {
        final int size = ThreadLocalRandom.current().nextInt(1, 20);
        final byte[] result = new byte[size];
        for(int i = 0; i < size; i++)
            result[i] = generator.generate();

        return result;
    }
}
