package io.dummymaker.generator.simple.string;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates URI as string
 *
 * @author Anton Kurako (GoodforGod)
 * @since 4.5.2020
 */
public class UriGenerator extends NounGenerator {

    private final Pattern pattern = Pattern.compile("uri|resource|path", CASE_INSENSITIVE);

    @Override
    public @NotNull String generate() {
        final StringBuilder builder = new StringBuilder("");
        final int total = ThreadLocalRandom.current().nextInt(1, 5);
        for (int i = 0; i < total; i++)
            builder.append("/").append(super.generate());

        return builder.toString();
    }

    @Override
    public @NotNull Pattern getPattern() {
        return pattern;
    }
}
