package io.dummymaker.generator.simple.number;


import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;


/**
 * Generates int from 0 to Integer.MAX_VALUE
 *
 * @author GoodforGod
 * @see Integer#MAX_VALUE
 * @since 05.03.2019
 */
public class IntegerGenerator implements IGenerator<Integer> {

    private final Pattern pattern = Pattern.compile("num(ber)?|counter|series", CASE_INSENSITIVE);

    @Override
    public @NotNull Integer generate() {
        return CollectionUtils.random(Integer.MAX_VALUE);
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }

    @Override
    public int order() {
        return -60;
    }
}
