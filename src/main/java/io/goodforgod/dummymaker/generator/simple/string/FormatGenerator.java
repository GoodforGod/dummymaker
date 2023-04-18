package io.goodforgod.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.bundle.Bundle;
import io.goodforgod.dummymaker.bundle.FormatBundle;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.Localisation;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates programming format name
 *
 * @author Anton Kurako (GoodforGod)
 * @since 4.5.2020
 */
public final class FormatGenerator implements Generator<CharSequence> {

    private static final Bundle FORMATS = new FormatBundle();
    private static final Pattern PATTERN = Pattern.compile("protocol|format", CASE_INSENSITIVE);

    @Override
    public @NotNull String get() {
        return FORMATS.random(Localisation.ENGLISH);
    }

    @Override
    public @NotNull Hints hints() {
        return Hints.builder()
                .withPattern(PATTERN)
                .withPriority(60)
                .build();
    }
}
