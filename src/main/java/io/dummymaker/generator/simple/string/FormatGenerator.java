package io.dummymaker.generator.simple.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.FormatBundle;
import io.dummymaker.generator.IGenerator;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates programming format name
 *
 * @author Anton Kurako (GoodforGod)
 * @since 4.5.2020
 */
public class FormatGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("format", CASE_INSENSITIVE);

    private final IBundle<String> formats = new FormatBundle();

    @Nullable
    @Override
    public String generate() {
        return formats.getRandom();
    }

    @Override
    public @Nullable Pattern getPattern() {
        return pattern;
    }
}
