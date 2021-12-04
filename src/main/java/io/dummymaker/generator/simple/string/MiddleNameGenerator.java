package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.MiddleNameBundle;
import io.dummymaker.generator.IGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Middle name generator
 *
 * @author GoodforGod
 * @since 16.07.2019
 */
public class MiddleNameGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("middle(name)?|patronymic", CASE_INSENSITIVE);

    private static final IBundle bundle = new MiddleNameBundle();

    @Override
    public @NotNull String generate() {
        return bundle.random();
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }

    @Override
    public int order() {
        return -10;
    }
}
