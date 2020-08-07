package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates medium integer values between 100000 and 999999
 *
 * @author GoodforGod
 * @since 12.10.2019
 */
public class IntegetMediumGenerator implements IGenerator<Integer> {

    private final Pattern pattern = Pattern.compile("postal|code|index|zip(code)?", CASE_INSENSITIVE);

    @Override
    public @NotNull Integer generate() {
        return CollectionUtils.random(100000, 999999);
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }
}
