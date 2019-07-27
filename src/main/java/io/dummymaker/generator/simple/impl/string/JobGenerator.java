package io.dummymaker.generator.simple.impl.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.JobBundle;
import io.dummymaker.generator.simple.IGenerator;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Job name generator
 *
 * @author GoodforGod
 * @since 16.07.2019
 */
public class JobGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("job|work", CASE_INSENSITIVE);

    private final IBundle<String> bundle = new JobBundle();

    @Override
    public String generate() {
        return bundle.getRandom();
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
