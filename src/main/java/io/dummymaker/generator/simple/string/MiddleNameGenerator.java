package io.dummymaker.generator.simple.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.MiddleNameBundle;
import io.dummymaker.generator.IGenerator;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Middle name generator
 *
 * @author GoodforGod
 * @since 16.07.2019
 */
public class MiddleNameGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("middle(name)?", CASE_INSENSITIVE);

    private final IBundle<String> bundle = new MiddleNameBundle();

    @Override
    public String generate() {
        return bundle.getRandom();
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
