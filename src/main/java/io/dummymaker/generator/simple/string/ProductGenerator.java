package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.ProductBundle;
import io.dummymaker.generator.IGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Generates product names
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.7.2020
 */
public class ProductGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern
            .compile("association|administrative|academy|university|org(anization)?|product|good|supply|topic", CASE_INSENSITIVE);
    private static final IBundle bundle = new ProductBundle();

    @Nullable
    @Override
    public String generate() {
        return bundle.random();
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }
}
