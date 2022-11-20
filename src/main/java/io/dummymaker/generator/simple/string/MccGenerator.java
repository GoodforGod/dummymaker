package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.impl.MerchantBundle;
import io.dummymaker.generator.Generator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.Nullable;

/**
 * Bank MCC (Merchant Category Code) generator
 *
 * @author Anton Kurako (GoodforGod)
 * @since 27.08.2022
 */
public final class MccGenerator implements Generator<String> {

    private static final Bundle BUNDLE = new MerchantBundle();
    private static final Pattern PATTERN = Pattern.compile("mcc|MerchantCategoryCode", CASE_INSENSITIVE);

    @Override
    public String get() {
        return BUNDLE.random();
    }

    @Override
    public Pattern pattern() {
        return PATTERN;
    }
}
