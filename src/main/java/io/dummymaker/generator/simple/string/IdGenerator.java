package io.dummymaker.generator.simple.string;

import io.dummymaker.generator.Generator;
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
    public @NotNull Pattern pattern() {
        return PATTERN;
    }

    @Override
    public int order() {
        return -100;
    }
}
