package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.impl.ExtensionBundle;
import io.dummymaker.bundle.impl.NounBundle;
import io.dummymaker.generator.Generator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.Nullable;

/**
 * Generates file names with extensions
 *
 * @author Anton Kurako (GoodforGod)
 * @since 27.7.2020
 */
public final class FileGenerator implements Generator<String> {

    private static final Bundle EXTENSION_BUNDLE = new ExtensionBundle();
    private static final Bundle NOUN_BUNDLE = new NounBundle();
    private static final Pattern PATTERN = Pattern.compile("file|docum(ent)?", CASE_INSENSITIVE);

    @Override
    public String get() {
        return NOUN_BUNDLE.random() + "." + EXTENSION_BUNDLE.random();
    }

    @Override
    public Pattern pattern() {
        return PATTERN;
    }

    @Override
    public int order() {
        return -10;
    }
}
