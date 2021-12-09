package io.dummymaker.generator.simple.string;


import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;
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
public class LevelGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("level|lvl", CASE_INSENSITIVE);

    private static final List<String> types = Arrays.asList("error", "warn", "info", "debug", "trace");

    @SuppressWarnings("ConstantConditions")
    @Override
    public @NotNull String generate() {
        return CollectionUtils.random(types);
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }
}
