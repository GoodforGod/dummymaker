package io.goodforgod.dummymaker.generator.simple.string;

import io.goodforgod.dummymaker.generator.Generator;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

/**
 * Generates random string 36 character length
 *
 * @author Anton Kurako (GoodforGod)
 * @since 26.05.2017
 */
public final class IdBigGenerator implements Generator<CharSequence> {

    @Override
    public @NotNull String get() {
        return UUID.randomUUID() + "-" + UUID.randomUUID();
    }
}
