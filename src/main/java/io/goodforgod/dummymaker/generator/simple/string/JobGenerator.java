package io.goodforgod.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.bundle.Bundle;
import io.goodforgod.dummymaker.bundle.JobBundle;
import io.goodforgod.dummymaker.generator.GenParameters;
import io.goodforgod.dummymaker.generator.Localisation;
import io.goodforgod.dummymaker.generator.ParameterizedGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Job name generator
 *
 * @author Anton Kurako (GoodforGod)
 * @since 16.07.2019
 */
public final class JobGenerator implements ParameterizedGenerator<CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("qualification|job|work|position", CASE_INSENSITIVE);

    private static final Bundle JOB_BUNDLE = new JobBundle();

    @Override
    public String get(@NotNull GenParameters parameters) {
        return parameters.namingCase().apply(get(parameters.localisation())).toString();
    }

    @Override
    public String get() {
        return get(Localisation.ENGLISH);
    }

    private static String get(@NotNull Localisation localisation) {
        return JOB_BUNDLE.random(localisation);
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
