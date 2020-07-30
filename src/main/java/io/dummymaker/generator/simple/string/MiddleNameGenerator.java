package io.dummymaker.generator.simple.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.MiddleNameBundle;
import io.dummymaker.generator.IGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Middle name generator
 *
 * @author GoodforGod
 * @since 16.07.2019
 */
public class MiddleNameGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("middle(name)?", CASE_INSENSITIVE);

    private final IBundle bundle = new MiddleNameBundle();

    @Override
    public @NotNull String generate() {
        return bundle.random();
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }
}
