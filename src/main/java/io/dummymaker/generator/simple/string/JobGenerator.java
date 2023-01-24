package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.JobBundle;
import io.dummymaker.generator.Localisation;
import io.dummymaker.generator.LocalizedGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Job name generator
 *
 * @author Anton Kurako (GoodforGod)
 * @since 16.07.2019
 */
public final class JobGenerator implements LocalizedGenerator<String> {

    private static final Pattern PATTERN = Pattern.compile("qualification|job|work|position", CASE_INSENSITIVE);

    private static final Bundle JOB_BUNDLE = new JobBundle();

    @Override
    public @NotNull String get(@NotNull Localisation localisation) {
        return JOB_BUNDLE.random(localisation);
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
