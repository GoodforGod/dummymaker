package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.impl.JobBundle;
import io.dummymaker.generator.Generator;
import java.util.regex.Pattern;

/**
 * Generates roles
 *
 * @author Anton Kurako (GoodforGod)
 * @since 13.07.2020
 */
public final class RoleGenerator implements Generator<String> {

    private static final Pattern PATTERN = Pattern.compile("role", CASE_INSENSITIVE);

    private static final Bundle BUNDLE = new JobBundle();

    @Override
    public String get() {
        return BUNDLE.random().toLowerCase().replace(' ', '-');
    }

    @Override
    public Pattern pattern() {
        return PATTERN;
    }
}
