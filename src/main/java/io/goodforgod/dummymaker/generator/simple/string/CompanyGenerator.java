package io.goodforgod.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.bundle.Bundle;
import io.goodforgod.dummymaker.bundle.CompanyBundle;
import io.goodforgod.dummymaker.generator.GenParameters;
import io.goodforgod.dummymaker.generator.Localisation;
import io.goodforgod.dummymaker.generator.ParameterizedGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates company name as a string
 *
 * @author Anton Kurako (GoodforGod) (Anton Kurako)
 * @since 07.06.2017
 */
public final class CompanyGenerator implements ParameterizedGenerator<CharSequence> {

    private static final Bundle BUNDLE = new CompanyBundle();
    private static final Pattern PATTERN = Pattern.compile("org(anization)?|company|corp(oration)?|fund|business|shop|store",
            CASE_INSENSITIVE);

    @Override
    public String get(@NotNull GenParameters parameters) {
        return parameters.namingCase().apply(getValue(parameters.localisation())).toString();
    }

    @Override
    public CharSequence get() {
        return getValue(Localisation.ENGLISH);
    }

    private static @NotNull String getValue(@NotNull Localisation localisation) {
        return BUNDLE.random(localisation);
    }

    @Override
    public @NotNull Hints hints() {
        return Hints.builder()
                .withPattern(PATTERN)
                .withPriority(5)
                .build();
    }
}
