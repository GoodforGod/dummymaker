package io.goodforgod.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.util.RandomUtils;
import java.util.UUID;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates hex data
 *
 * @author Anton Kurako (GoodforGod)
 * @since 04.11.2018
 */
public final class HexDataGenerator implements Generator<CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("hex", CASE_INSENSITIVE);

    @Override
    public @NotNull String get() {
        final int total = RandomUtils.random(1, 5);

        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < total; i++) {
            builder.append(UUID.randomUUID());
        }

        return builder.toString().replace("-", "");
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
