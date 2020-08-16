package io.dummymaker.generator.simple.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.NounBundle;
import io.dummymaker.generator.IGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates single noun word
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class NounGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("word|noun|field|data(base)?|schema|collection|class", CASE_INSENSITIVE);

    private final IBundle bundle = new NounBundle();

    @Override
    public @NotNull String generate() {
        return bundle.random();
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }
}
