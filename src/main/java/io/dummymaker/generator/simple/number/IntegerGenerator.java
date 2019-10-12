package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.IGenerator;

import java.util.concurrent.ThreadLocalRandom;
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
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public Integer generate() {
        return ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
    }
}
