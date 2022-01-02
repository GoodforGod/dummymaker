package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.ExtensionBundle;
import io.dummymaker.generator.IGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.Nullable;

/**
 * Generates files extensions
 *
 * @author Anton Kurako (GoodforGod)
 * @since 27.7.2020
 */
public class ExtensionGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("ext(ension)?", CASE_INSENSITIVE);
    private static final IBundle bundle = new ExtensionBundle();

    @Nullable
    @Override
    public String generate() {
        return bundle.random();
    }

    @Override
    public @Nullable Pattern pattern() {
        return pattern;
    }
}
