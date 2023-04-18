package io.goodforgod.dummymaker.generator.simple;

import io.goodforgod.dummymaker.generator.Generator;
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
