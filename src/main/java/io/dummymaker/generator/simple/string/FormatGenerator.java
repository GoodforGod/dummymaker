package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.FormatBundle;
import io.dummymaker.generator.IGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates programming format name
 *
 * @author Anton Kurako (GoodforGod)
 * @since 4.5.2020
 */
public class FormatGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("format", CASE_INSENSITIVE);

    private final IBundle formats = new FormatBundle();

    @Override
    public @NotNull String generate() {
        return formats.random();
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }
}
