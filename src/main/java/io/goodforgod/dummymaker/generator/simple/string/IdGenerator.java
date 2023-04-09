package io.goodforgod.dummymaker.generator.simple.string;

import io.goodforgod.dummymaker.generator.Generator;
import java.util.UUID;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates id based on UUID
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.02.2018
 */
public final class IdGenerator implements Generator<CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("[UuGg]?[Uu]?[Ii]d$|^[UuGg]?[Uu]?[Ii]d");

    @Override
    public @NotNull String get() {
        return UUID.randomUUID().toString();
    }

    @Override
    public @NotNull Hints hints() {
        return Hints.builder()
                .withPattern(PATTERN)
                .withPriority(100)
                .build();
    }
}
