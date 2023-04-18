package io.goodforgod.dummymaker.generator.simple;

import io.goodforgod.dummymaker.generator.Generator;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

/**
 * Generates UUIDs
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.02.2018
 */
public final class UuidGenerator implements Generator<UUID> {

    @Override
    public @NotNull UUID get() {
        return UUID.randomUUID();
    }
}
