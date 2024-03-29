package io.goodforgod.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.bundle.Bundle;
import io.goodforgod.dummymaker.bundle.ExtensionBundle;
import io.goodforgod.dummymaker.bundle.NounBundle;
import io.goodforgod.dummymaker.generator.GenParameters;
import io.goodforgod.dummymaker.generator.Localisation;
import io.goodforgod.dummymaker.generator.ParameterizedGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates file names with extensions
 *
 * @author Anton Kurako (GoodforGod)
 * @since 27.7.2020
 */
public final class FileGenerator implements ParameterizedGenerator<CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("file|docum(ent)?", CASE_INSENSITIVE);

    private static final Bundle EXTENSION_BUNDLE = new ExtensionBundle();
    private static final Bundle NOUN_BUNDLE = new NounBundle();

    @Override
    public String get(@NotNull GenParameters parameters) {
        return parameters.namingCase().apply(NOUN_BUNDLE.random(parameters.localisation())) + "."
                + EXTENSION_BUNDLE.random(parameters.localisation());
    }

    @Override
    public String get() {
        return NOUN_BUNDLE.random(Localisation.ENGLISH) + "." + EXTENSION_BUNDLE.random(Localisation.ENGLISH);
    }

    @Override
    public @NotNull Hints hints() {
        return Hints.builder()
                .withPattern(PATTERN)
                .withPriority(10)
                .build();
    }
}
