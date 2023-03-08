package io.dummymaker.generator.simple;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.GenParameters;
import io.dummymaker.generator.ParameterizedGenerator;
import io.dummymaker.generator.simple.string.NounGenerator;
import io.dummymaker.util.RandomUtils;
import java.net.URI;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates URI as string
 *
 * @author Anton Kurako (GoodforGod)
 * @since 12.11.2022
 */
public final class UriGenerator implements ParameterizedGenerator<URI> {

    private static final Pattern PATTERN = Pattern.compile("uri|resource|path", CASE_INSENSITIVE);

    private static final NounGenerator NOUN_GENERATOR = new NounGenerator();

    @Override
    public URI get(@NotNull GenParameters parameters) {
        return get(() -> NOUN_GENERATOR.get(parameters));
    }

    @Override
    public URI get() {
        return get(NOUN_GENERATOR::get);
    }

    private static URI get(Supplier<String> pathSupplier) {
        final int total = RandomUtils.random(1, 5);

        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < total; i++) {
            builder.append("/").append(pathSupplier.get());
        }

        return URI.create(builder.toString());
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
