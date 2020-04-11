package io.dummymaker.generator.simple.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.JobBundle;
import io.dummymaker.generator.IGenerator;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Job name generator
 *
 * @author GoodforGod
 * @since 16.07.2019
 */
public class JobGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("job|work|position", CASE_INSENSITIVE);

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
