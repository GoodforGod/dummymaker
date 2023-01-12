package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.RandomUtils;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Cadastral number generator
 *
 * @author Anton Kurako (GoodforGod)
 * @since 12.10.2019
 */
public final class CadastralGenerator implements Generator<String> {

    private static final Pattern PATTERN = Pattern.compile("cadastral|cad(num)?", CASE_INSENSITIVE);

    @Override
    public @NotNull String get() {
        return String.format("%s:%s:%s:%s",
                RandomUtils.random(10, 99),
                RandomUtils.random(10, 99),
                RandomUtils.random(100000, 9999999),
                RandomUtils.random(10, 99));
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
