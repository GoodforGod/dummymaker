package io.dummymaker.generator.simple.impl.number;

import io.dummymaker.generator.simple.IGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Byte value from 0 to Byte.MAX_VALUE
 *
 * @author GoodforGod
 * @since 06.03.2019
 */
public class UByteGenerator implements IGenerator<Byte> {

    @Override
    public Byte generate() {
        return (byte) ThreadLocalRandom.current().nextInt(0, Byte.MAX_VALUE);
    }
}
