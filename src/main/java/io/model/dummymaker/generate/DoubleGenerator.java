package io.model.dummymaker.generate;


import java.util.concurrent.ThreadLocalRandom;

/**
 * Default Comment
 *
 * @author @GoodforGod
 * @since 26.05.2017
 */
public class DoubleGenerator implements IGenerator<Double> {

    @Override
    public Double generate() {
        return ThreadLocalRandom.current().nextDouble();
    }
}
