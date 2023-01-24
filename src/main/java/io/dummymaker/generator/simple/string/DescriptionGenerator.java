package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.PhraseBundle;
import io.dummymaker.generator.Localisation;
import io.dummymaker.generator.LocalizedGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates phrase description analog
 *
 * @author Anton Kurako (GoodforGod)
 * @since 20.07.2019
 */
public final class DescriptionGenerator implements LocalizedGenerator<String> {

    private static final Bundle BUNDLE = new PhraseBundle();
    private static final Pattern PATTERN = Pattern.compile("diploma|info|desc(ription)?|phrase|comment|sentence|reason",
            CASE_INSENSITIVE);

    @Override
    public @NotNull String get(@NotNull Localisation localisation) {
        return BUNDLE.random(localisation);
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }

    @Override
    public int order() {
        return -10;
    }
}
