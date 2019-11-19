package io.dummymaker.generator.simple.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.CityBundle;
import io.dummymaker.generator.IGenerator;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates city in english as a string
 *
 * @author GoodforGod (Anton Kurako)
 * @since 07.06.2017
 */
public class CityGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("city|town|village|settlement", CASE_INSENSITIVE);

    private final IBundle<String> bundle = new CityBundle();

    @Override
    public String generate() {
        return bundle.getRandom();
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
