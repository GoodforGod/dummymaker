package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.JobBundle;
import io.dummymaker.generator.Localisation;
import io.dummymaker.generator.LocalizedGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates roles
 *
 * @author Anton Kurako (GoodforGod)
 * @since 13.07.2020
 */
public final class RoleGenerator implements LocalizedGenerator<String> {

    private static final Pattern PATTERN = Pattern.compile("role", CASE_INSENSITIVE);

    private static final Bundle BUNDLE = new JobBundle();

    @Override
    public String get(@NotNull Localisation localisation) {
        return BUNDLE.random(localisation).toLowerCase().replace(' ', '-');
    }

    @Override
    public Pattern pattern() {
        return PATTERN;
    }
}
