package io.dummymaker.generator.simple;

import io.dummymaker.generator.Generator;
import io.dummymaker.generator.simple.string.NounGenerator;
import io.dummymaker.util.RandomUtils;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates URI as string
 *
 * @author Anton Kurako (GoodforGod)
 * @since 12.11.2022
 */
public final class UriGenerator implements Generator<URI> {

    private static final Pattern PATTERN = Pattern.compile("uri|resource|path", CASE_INSENSITIVE);

    private static final Generator<String> GENERATOR = new NounGenerator();

    @Override
    public @NotNull URI get() {
        final int total = RandomUtils.random(1, 5);

        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < total; i++) {
            builder.append("/").append(GENERATOR.get());
        }

        return URI.create(builder.toString());
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
