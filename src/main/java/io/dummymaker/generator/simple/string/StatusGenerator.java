package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.CollectionUtils;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates status from one of lists
 *
 * @author Anton Kurako (GoodforGod)
 * @since 04.12.2021
 */
public final class StatusGenerator implements Generator<String> {

    private static final Pattern PATTERN = Pattern.compile("status|type", CASE_INSENSITIVE);

    private static final List<String> TYPES = Arrays.asList("success", "failed", "rejected", "invalid");

    @SuppressWarnings("ConstantConditions")
    @Override
    public @NotNull String get() {
        return CollectionUtils.random(TYPES);
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
