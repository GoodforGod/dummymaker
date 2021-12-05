package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.DistrictBundle;
import io.dummymaker.generator.IGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates district
 *
 * @author GoodforGod
 * @since 16.07.2019
 */
public class DistrictGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("state|region|district|locale?", CASE_INSENSITIVE);

    private static final IBundle bundle = new DistrictBundle();

    @Override
    public @NotNull String generate() {
        return bundle.random();
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }
}
