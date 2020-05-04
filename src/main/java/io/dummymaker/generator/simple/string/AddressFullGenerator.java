package io.dummymaker.generator.simple.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.CountryBundle;
import io.dummymaker.generator.IGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Country with address
 *
 * @author GoodforGod
 * @see AddressGenerator
 * @since 16.07.2019
 */
public class AddressFullGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("addr(ess)?full|place|residence|home|location", CASE_INSENSITIVE);

    private final IGenerator<String> generator = new AddressGenerator();
    private final IBundle<String> countryBundle = new CountryBundle();

    @Override
    public @NotNull String generate() {
        return countryBundle.getRandom() + ", " + generator.generate();
    }

    @Override
    public @NotNull Pattern getPattern() {
        return pattern;
    }
}
