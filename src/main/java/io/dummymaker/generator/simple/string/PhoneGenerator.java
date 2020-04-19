package io.dummymaker.generator.simple.string;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;

import java.util.regex.Pattern;

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
        return CollectionUtils.random(1, 9)
                + "("
                + CollectionUtils.random(100, 999)
                + ")"
                + CollectionUtils.random(100, 999)
                + CollectionUtils.random(1000, 9999);
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
