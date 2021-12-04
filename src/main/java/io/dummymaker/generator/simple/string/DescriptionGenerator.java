package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.PhraseBundle;
import io.dummymaker.generator.IGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates phrase description analog
 *
 * @author GoodforGod
 * @since 20.07.2019
 */
public class DescriptionGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("diploma|info|desc(ription)?|phrase|comment|sentence|reason",
            CASE_INSENSITIVE);

    private static final IBundle bundle = new PhraseBundle();

    @Override
    public @NotNull String generate() {
        return bundle.random();
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }
}
