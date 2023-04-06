package io.goodforgod.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.bundle.Bundle;
import io.goodforgod.dummymaker.bundle.ExtensionBundle;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.Localisation;
import java.util.regex.Pattern;

/**
 * Generates files extensions
 *
 * @author Anton Kurako (GoodforGod)
 * @since 27.7.2020
 */
public final class ExtensionGenerator implements Generator<CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("ext(ension)?", CASE_INSENSITIVE);

    private static final Bundle BUNDLE = new ExtensionBundle();

    @Override
    public String get() {
        return BUNDLE.random(Localisation.ENGLISH);
    }

    @Override
    public Pattern pattern() {
        return PATTERN;
    }
}
