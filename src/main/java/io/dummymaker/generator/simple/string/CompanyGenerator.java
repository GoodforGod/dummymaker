package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.CompanyBundle;
import io.dummymaker.generator.Localisation;
import io.dummymaker.generator.LocalizedGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates company name as a string
 *
 * @author Anton Kurako (GoodforGod) (Anton Kurako)
 * @since 07.06.2017
 */
public final class CompanyGenerator implements LocalizedGenerator<String> {

    private static final Bundle BUNDLE = new CompanyBundle();
    private static final Pattern PATTERN = Pattern.compile("org(anization)?|company|corp(oration)?|fund|business|shop|store",
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
        return -5;
    }
}
