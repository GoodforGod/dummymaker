package io.dummymaker.generator.simple.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.NounBundle;
import io.dummymaker.generator.IGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class NounGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("word|noun|field", CASE_INSENSITIVE);

    private final IBundle<String> bundle = new NounBundle();

    @Override
    public @NotNull String generate() {
        return bundle.getRandom();
    }

    @Override
    public @NotNull Pattern getPattern() {
        return pattern;
    }
}
