package io.dummymaker.generator.simple.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.SurnameBundle;
import io.dummymaker.generator.IGenerator;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Surname generator
 *
 * @author GoodforGod
 * @since 06.03.2019
 */
public class SurnameGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("surname", CASE_INSENSITIVE);

    private final IBundle<String> bundle = new SurnameBundle();

    @Override
    public String generate() {
        return bundle.getRandom();
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
