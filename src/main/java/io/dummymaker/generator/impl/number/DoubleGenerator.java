package io.dummymaker.generator.impl.number;


import io.dummymaker.generator.IGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates double from 0 to 1
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class DoubleGenerator implements IGenerator<Double> {

    @Override
    public Double generate() {
        return ThreadLocalRandom.current().nextDouble();
    }
}
