package io.dummymaker.generator.simple.number;


import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;


/**
 * Generates medium integer values between 100000 and 999999
 *
 * @author GoodforGod
 * @since 12.10.2019
 */
public class IntegetMediumGenerator implements IGenerator<Integer> {

    private final Pattern pattern = Pattern.compile("number|postal|code|index|zip(code)?", CASE_INSENSITIVE);

    @Override
    public @NotNull Integer generate() {
        return CollectionUtils.random(100000, 999999);
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }

    @Override
    public int order() {
        return -50;
    }
}
