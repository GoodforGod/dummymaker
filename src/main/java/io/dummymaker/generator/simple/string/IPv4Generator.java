package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.RandomUtils;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 21.01.2023
 */
public final class IPv4Generator implements Generator<CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("ip|ipv4", CASE_INSENSITIVE);

    @Override
    public @NotNull String get() {
        return RandomUtils.random(0, 255)
                + "." + RandomUtils.random(0, 255)
                + "." + RandomUtils.random(0, 255)
                + "." + RandomUtils.random(0, 255);
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
