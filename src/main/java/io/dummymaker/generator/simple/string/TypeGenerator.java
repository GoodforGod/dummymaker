package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.CollectionUtils;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates type as one of adjectives ladders level of 'Good, Bad'
 *
 * @author Anton Kurako (GoodforGod)
 * @since 12.4.2020
 */
public final class TypeGenerator implements Generator<String> {

    private static final Pattern PATTERN = Pattern.compile("adjective|intense|difficult|level|lvl", CASE_INSENSITIVE);

    private static final List<String> TYPES = Arrays.asList("TERRIBLE", "BAD", "POOR", "LIMITED",
            "NEUTRAL", "AVERAGE", "DECENT", "FINE", "SUPERIOR");

    @SuppressWarnings("ConstantConditions")
    @Override
    public @NotNull String get() {
        return RandomUtils.random(TYPES);
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
