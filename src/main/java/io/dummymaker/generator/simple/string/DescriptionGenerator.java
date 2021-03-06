package io.dummymaker.generator.simple.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.PhraseBundle;
import io.dummymaker.generator.IGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates phrase description analog
 *
 * @author GoodforGod
 * @since 20.07.2019
 */
public class DescriptionGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("info|desc(ription)?|phrase|comment|sentence", CASE_INSENSITIVE);

    private final IBundle bundle = new PhraseBundle();

    @Override
    public @NotNull String generate() {
        return bundle.random();
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }
}
