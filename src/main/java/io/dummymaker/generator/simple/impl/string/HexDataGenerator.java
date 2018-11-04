package io.dummymaker.generator.simple.impl.string;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates hex data
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class HexDataGenerator extends IdBigGenerator {

    @Override
    public String generate() {
        return (super.generate() + super.generate()).substring(0, ThreadLocalRandom.current().nextInt(16, 128));
    }
}
