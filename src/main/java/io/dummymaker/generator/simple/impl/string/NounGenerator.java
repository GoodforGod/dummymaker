package io.dummymaker.generator.simple.impl.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.NounBundle;
import io.dummymaker.generator.simple.IGenerator;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class NounGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("word|noun", CASE_INSENSITIVE);

    private final IBundle<String> bundle = new NounBundle();

    @Override
    public String generate() {
        return bundle.getRandom();
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
