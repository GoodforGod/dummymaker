package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates int from 0 to Integer.MAX_VALUE
 *
 * @author GoodforGod
 * @see Integer#MAX_VALUE
 * @since 05.03.2019
 */
public class IntegerGenerator implements IGenerator<Integer> {

    private final Pattern pattern = Pattern.compile("num(ber)?|counter", CASE_INSENSITIVE);

    @Override
    public @NotNull Integer generate() {
        return CollectionUtils.random(Integer.MAX_VALUE);
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }
}
