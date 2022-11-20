package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.impl.ExtensionBundle;
import io.dummymaker.generator.Generator;
import java.util.regex.Pattern;

/**
 * Generates files extensions
 *
 * @author Anton Kurako (GoodforGod)
 * @since 27.7.2020
 */
public final class ExtensionGenerator implements Generator<String> {

    private static final Bundle BUNDLE = new ExtensionBundle();
    private static final Pattern PATTERN = Pattern.compile("ext(ension)?", CASE_INSENSITIVE);

    @Override
    public String get() {
        return BUNDLE.random();
    }

    @Override
    public Pattern pattern() {
        return PATTERN;
    }
}
