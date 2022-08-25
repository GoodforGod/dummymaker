package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.FemaleNameBundle;
import io.dummymaker.bundle.impl.MaleNameBundle;
import io.dummymaker.generator.IGenerator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates names male and female as a string
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class NameGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("user|account|name|assignee|employe|worker", CASE_INSENSITIVE);

    private static final IBundle maleBundle = new MaleNameBundle();
    private static final IBundle femaleBundle = new FemaleNameBundle();

    @Override
    public @NotNull String generate() {
        return ThreadLocalRandom.current().nextBoolean()
                ? maleBundle.random()
                : femaleBundle.random();
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }

    @Override
    public int order() {
        return -60;
    }
}
