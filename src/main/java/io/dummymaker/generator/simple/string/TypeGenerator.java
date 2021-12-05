package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.IGenerator;
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
public class TypeGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("adjective|intense|difficult|level|lvl", CASE_INSENSITIVE);

    private static final List<String> types = Arrays.asList("terrible", "bad", "poor", "limited",
            "neutral", "average", "decent", "fine", "superior");

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
