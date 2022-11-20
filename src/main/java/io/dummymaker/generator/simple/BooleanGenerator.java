package io.dummymaker.generator.simple;

import io.dummymaker.generator.Generator;
import java.util.concurrent.ThreadLocalRandom;
import org.jetbrains.annotations.NotNull;

/**
 * Generates boolean values
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.02.2018
 */
public final class BooleanGenerator implements Generator<Boolean> {

    @Override
    public @NotNull Boolean get() {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
