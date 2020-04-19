package io.dummymaker.generator.simple.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.FemaleNameBundle;
import io.dummymaker.bundle.impl.MaleNameBundle;
import io.dummymaker.generator.IGenerator;
import io.dummymaker.util.CollectionUtils;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates names male and female as a string
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class NameGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("name", CASE_INSENSITIVE);

    private final IBundle<String> maleBundle = new MaleNameBundle();
    private final IBundle<String> femaleBundle = new FemaleNameBundle();

    @Override
    public String generate() {
        int gender = CollectionUtils.random(100);

        return (gender > 50)
                ? maleBundle.getRandom()
                : femaleBundle.getRandom();
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
