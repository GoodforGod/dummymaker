package io.dummymaker.generator.simple;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.Localisation;
import io.dummymaker.generator.LocalizedGenerator;
import io.dummymaker.generator.simple.string.NounGenerator;
import io.dummymaker.util.RandomUtils;
import java.net.URI;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates URI as string
 *
 * @author Anton Kurako (GoodforGod)
 * @since 12.11.2022
 */
public final class UriGenerator implements LocalizedGenerator<URI> {

    private static final Pattern PATTERN = Pattern.compile("uri|resource|path", CASE_INSENSITIVE);

    private static final LocalizedGenerator<String> NOUN_GENERATOR = new NounGenerator();

    @Override
    public @NotNull URI get(@NotNull Localisation localisation) {
        final int total = RandomUtils.random(1, 5);

        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < total; i++) {
            builder.append("/").append(NOUN_GENERATOR.get(localisation));
        }

        return URI.create(builder.toString());
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
