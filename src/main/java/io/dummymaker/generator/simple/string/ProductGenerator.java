package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.ProductBundle;
import io.dummymaker.generator.GenParameters;
import io.dummymaker.generator.Localisation;
import io.dummymaker.generator.ParameterizedGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates product names
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.7.2020
 */
public final class ProductGenerator implements ParameterizedGenerator<CharSequence> {

    private static final Pattern PATTERN = Pattern
            .compile("association|administrative|academy?|university|org(anization)?|product|good|supply|topic",
                    CASE_INSENSITIVE);

    private static final Bundle BUNDLE = new ProductBundle();

    @Override
    public String get(@NotNull GenParameters parameters) {
        return parameters.namingCase().apply(get(parameters.localisation())).toString();
    }

    @Override
    public String get() {
        return get(Localisation.ENGLISH);
    }

    private static String get(@NotNull Localisation localisation) {
        return BUNDLE.random(localisation);
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
