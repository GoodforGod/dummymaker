package io.dummymaker.generator.simple.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.StreetBundle;
import io.dummymaker.generator.IGenerator;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Street name generator
 *
 * @author GoodforGod
 * @since 16.07.2019
 */
public class StreetGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("street", CASE_INSENSITIVE);

    private final IBundle<String> bundle = new StreetBundle();

    @Override
    public String generate() {
        return bundle.getRandom();
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}