package io.dummymaker.generator.simple.impl.array;

import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.number.DoubleBigGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates array of doubles
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class DoubleArrayGenerator implements IGenerator<double[][]> {

    private final DoubleBigGenerator generator = new DoubleBigGenerator();

    @Override
    public double[][] generate() {
        final int size = ThreadLocalRandom.current().nextInt(1, 20);
        final double[][] result = new double[size][size];
        for(int i = 0; i < size; i++)
            result[i][i] = generator.generate();

        return result;
    }
}
