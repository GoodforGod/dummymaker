package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.NounBundle;
import io.dummymaker.generator.Localisation;
import io.dummymaker.generator.LocalizedGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates single noun word
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.02.2018
 */
public final class NounGenerator implements LocalizedGenerator<String> {

    private static final Pattern PATTERN = Pattern.compile(
            "record|commission|education|subj(ect)?|program|word|noun|field|data(base)?|schema|collection|class",
            CASE_INSENSITIVE);

    private static final Bundle BUNDLE = new NounBundle();

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
