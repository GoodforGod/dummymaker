package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates small integer numbers from 1 up to 100
 *
 * @author GoodforGod
 * @since 25.07.2019
 */
public class IntegerSmallGenerator implements IGenerator<Integer> {

    private final Pattern pattern = Pattern.compile("age|grade|group", CASE_INSENSITIVE);

    @Override
    public @NotNull Integer generate() {
        return CollectionUtils.random(1, 99);
    }

    @Override
    public @NotNull Pattern getPattern() {
        return pattern;
    }
}
