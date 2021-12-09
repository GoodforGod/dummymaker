package io.dummymaker.generator.simple.number;


import io.dummymaker.generator.IGenerator;
import java.util.concurrent.ThreadLocalRandom;
import org.jetbrains.annotations.NotNull;


/**
 * Generates double from 0 to 1
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class DoubleGenerator implements IGenerator<Double> {

    @Override
    public @NotNull Double generate() {
        return ThreadLocalRandom.current().nextDouble();
    }
}
