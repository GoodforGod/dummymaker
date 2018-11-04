package io.dummymaker.generator.simple.impl.number;

import io.dummymaker.generator.simple.IGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates byte from 0 to 255
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class ByteGenerator implements IGenerator<Byte> {

    @Override
    public Byte generate() {
        return (byte) ThreadLocalRandom.current().nextInt(0, 255);
    }
}
