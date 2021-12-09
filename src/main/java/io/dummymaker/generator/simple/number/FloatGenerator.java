package io.dummymaker.generator.simple.number;


import io.dummymaker.generator.IGenerator;
import java.util.concurrent.ThreadLocalRandom;
import org.jetbrains.annotations.NotNull;


/**
 * Generates float number from 0 to 1
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class FloatGenerator implements IGenerator<Float> {

    @Override
    public @NotNull Float generate() {
        return ThreadLocalRandom.current().nextFloat();
    }
}
