package io.dummymaker.generator.simple.impl.number;

import io.dummymaker.generator.simple.IGenerator;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

/**
 * Generates int from 0 to Integer.MAX_VALUE
 *
 * @author GoodforGod
 * @see Integer#MAX_VALUE
 * @since 05.03.2019
 */
public class UIntegerGenerator implements IGenerator<Integer> {

    private final Pattern pattern = Pattern.compile("num(ber)?");

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public Integer generate() {
        return ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
    }
}
