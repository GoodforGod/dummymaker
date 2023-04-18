package io.goodforgod.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.util.CollectionUtils;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates level from one of present
 *
 * @author Anton Kurako (GoodforGod)
 * @since 04.12.2021
 */
public final class LevelGenerator implements Generator<CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("level|lvl", CASE_INSENSITIVE);

    private static final List<String> TYPES_EN = Arrays.asList("error", "warn", "info", "debug", "trace");

    @Override
    public String get() {
        return CollectionUtils.random(TYPES_EN);
    }

    @Override
    public @NotNull Hints hints() {
        return Hints.builder()
                .withPattern(PATTERN)
                .build();
    }
}
