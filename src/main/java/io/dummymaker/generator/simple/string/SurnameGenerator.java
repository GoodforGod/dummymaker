package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.SurnameBundle;
import io.dummymaker.generator.Localisation;
import io.dummymaker.generator.LocalizedGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Surname generator
 *
 * @author Anton Kurako (GoodforGod)
 * @since 06.03.2019
 */
public final class SurnameGenerator implements LocalizedGenerator<String> {

    private static final Pattern PATTERN = Pattern.compile("surname", CASE_INSENSITIVE);

    private static final Bundle BUNDLE = new SurnameBundle();

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
