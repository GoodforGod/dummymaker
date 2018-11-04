package io.dummymaker.generator.simple.impl.number;

import io.dummymaker.generator.simple.IGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates float number from 0 to 1
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class FloatGenerator implements IGenerator<Float> {

    @Override
    public Float generate() {
        return ThreadLocalRandom.current().nextFloat();
    }
}
