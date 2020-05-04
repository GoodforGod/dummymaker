package io.dummymaker.generator.simple.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.DistrictBundle;
import io.dummymaker.generator.IGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates district
 *
 * @author GoodforGod
 * @since 16.07.2019
 */
public class DistrictGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("state|district|locale?", CASE_INSENSITIVE);

    private final IBundle<String> bundle = new DistrictBundle();

    @Override
    public @NotNull String generate() {
        return bundle.getRandom();
    }

    @Override
    public @NotNull Pattern getPattern() {
        return pattern;
    }
}
