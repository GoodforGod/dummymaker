package io.dummymaker.generator.simple.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.ProductBundle;
import io.dummymaker.generator.IGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates product names
 *
 * @author Anton Kurako (GoodforGod)
 * @since 21.7.2020
 */
public class ProductGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("product|good|supply|topic", CASE_INSENSITIVE);
    private final IBundle bundle = new ProductBundle();

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
