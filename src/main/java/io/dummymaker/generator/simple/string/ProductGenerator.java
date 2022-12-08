package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.ProductBundle;
import io.dummymaker.generator.Generator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates product names
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.7.2020
 */
public final class ProductGenerator implements Generator<String> {

    private static final Pattern PATTERN = Pattern
            .compile("association|administrative|academy?|university|org(anization)?|product|good|supply|topic",
                    CASE_INSENSITIVE);

    private static final Bundle BUNDLE = new ProductBundle();

    @Override
    public String get() {
        return BUNDLE.random();
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
