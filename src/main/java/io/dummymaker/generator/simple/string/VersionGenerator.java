package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.RandomUtils;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates version as string (like 1.1.0-SNAPSHOT) or without SNAPSHOT suffix
 *
 * @author Anton Kurako (GoodforGod)
 * @since 6.4.2020
 */
public final class VersionGenerator implements Generator<CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("ver(sion)?", CASE_INSENSITIVE);

    @Override
    public @NotNull String get() {
        final String suffix = ThreadLocalRandom.current().nextBoolean()
                ? "-SNAPSHOT"
                : "";

        return RandomUtils.random(1, 9)
                + "."
                + RandomUtils.random(1, 99)
                + "."
                + RandomUtils.random(1, 99)
                + suffix;
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
