package io.goodforgod.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.bundle.Bundle;
import io.goodforgod.dummymaker.bundle.PhraseBundle;
import io.goodforgod.dummymaker.generator.GenParameters;
import io.goodforgod.dummymaker.generator.Localisation;
import io.goodforgod.dummymaker.generator.ParameterizedGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates phrase description analog
 *
 * @author Anton Kurako (GoodforGod)
 * @since 20.07.2019
 */
public final class DescriptionGenerator implements ParameterizedGenerator<CharSequence> {

    private static final Bundle BUNDLE = new PhraseBundle();
    private static final Pattern PATTERN = Pattern.compile("diploma|info|desc(ription)?|phrase|comment|sentence|reason",
            CASE_INSENSITIVE);

    @Override
    public CharSequence get(@NotNull GenParameters parameters) {
        return parameters.namingCase().apply(get(parameters.localisation())).toString();
    }

    @Override
    public CharSequence get() {
        return get(Localisation.ENGLISH);
    }

    private static String get(@NotNull Localisation localisation) {
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
