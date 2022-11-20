package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.Generator;
import java.util.UUID;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates random string like "aag2151tgdsfa9352tf"
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.02.2018
 */
public final class StringGenerator implements Generator<String> {

    private static final Pattern PATTERN = Pattern.compile("str", CASE_INSENSITIVE);

    @Override
    public @NotNull String get() {
        final String s = UUID.randomUUID().toString() + UUID.randomUUID();
        return s.replace("-", "");
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
