package io.dummymaker.generator.simple.impl.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.CountryBundle;
import io.dummymaker.generator.simple.IGenerator;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates country as a string
 *
 * @author GoodforGod (Anton Kurako)
 * @since 07.06.2017
 */
public class CountryGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("country", CASE_INSENSITIVE);

    private final IBundle<String> bundle = new CountryBundle();

    @Override
    public String generate() {
        return bundle.getRandom();
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
