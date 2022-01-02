package io.dummymaker.generator.simple;

import io.dummymaker.generator.IGenerator;
import java.util.concurrent.ThreadLocalRandom;
import org.jetbrains.annotations.NotNull;

/**
 * Generates boolean values
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class BooleanGenerator implements IGenerator<Boolean> {

    @Override
    public @NotNull Boolean generate() {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
