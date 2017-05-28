package io.generator.stock;


import io.generator.IGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 26.05.2017
 */
public class DoubleGenerator implements IGenerator<Double> {

    public Double generate() {
        return ThreadLocalRandom.current().nextDouble();
    }
}
