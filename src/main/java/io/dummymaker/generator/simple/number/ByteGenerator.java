package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.IGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates byte from -127 to 128
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class ByteGenerator implements IGenerator<Byte> {

    @Override
    public Byte generate() {
        return (byte) ThreadLocalRandom.current().nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
    }
}
