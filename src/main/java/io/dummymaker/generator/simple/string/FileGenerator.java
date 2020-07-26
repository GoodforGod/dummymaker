package io.dummymaker.generator.simple.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.ExtensionBundle;
import io.dummymaker.bundle.impl.NounBundle;
import io.dummymaker.generator.IGenerator;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates file names with extensions
 *
 * @author Anton Kurako (GoodforGod)
 * @since 27.7.2020
 */
public class FileGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("file", CASE_INSENSITIVE);
    private final IBundle extensions = new ExtensionBundle();
    private final IBundle names = new NounBundle();

    @Nullable
    @Override
    public String generate() {
        return names.random() + "." + extensions.random();
    }

    @Override
    public @Nullable Pattern getPattern() {
        return pattern;
    }
}
