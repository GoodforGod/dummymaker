package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.impl.TagsBundle;
import io.dummymaker.generator.Generator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates tag as a string like #tag
 *
 * @author Anton Kurako (GoodforGod) (Anton Kurako)
 * @since 07.06.2017
 */
public final class TagGenerator implements Generator<String> {

    private static final Pattern PATTERN = Pattern.compile("(hash)?tag", CASE_INSENSITIVE);

    private static final Bundle BUNDLE = new TagsBundle();

    @Override
    public @NotNull String get() {
        return BUNDLE.random();
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
