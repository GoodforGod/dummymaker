package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.IGenerator;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates medium integer values between 100000 and 999999
 *
 * @author GoodforGod
 * @since 12.10.2019
 */
public class IntegetMediumGenerator implements IGenerator<Integer> {

    private final Pattern pattern = Pattern.compile("postal|code|index ", CASE_INSENSITIVE);

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public Integer generate() {
        return ThreadLocalRandom.current().nextInt(100000, 999999);
    }
}
