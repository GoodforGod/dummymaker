package io.dummymaker.generator.simple.impl.string;


import io.dummymaker.generator.simple.IGenerator;

import java.util.regex.Pattern;

import static java.util.concurrent.ThreadLocalRandom.current;
import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates simple mobile phone as a string
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class PhoneGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("phone", CASE_INSENSITIVE);

    @Override
    public String generate() {
        return current().nextInt(1, 9)
                + "("
                + current().nextInt(100, 999)
                + ")"
                + current().nextInt(100, 999)
                + current().nextInt(1000, 9999);
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
