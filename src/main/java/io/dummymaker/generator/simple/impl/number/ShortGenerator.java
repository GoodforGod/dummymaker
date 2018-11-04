package io.dummymaker.generator.simple.impl.number;

import io.dummymaker.generator.simple.IGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates short numbers
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class ShortGenerator implements IGenerator<Short> {

    @Override
    public Short generate() {
        return (short) ThreadLocalRandom.current().nextInt(0, 65535);
    }
}
