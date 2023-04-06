package io.goodforgod.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.bundle.Bundle;
import io.goodforgod.dummymaker.bundle.MerchantBundle;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.Localisation;
import java.util.regex.Pattern;

/**
 * Bank MCC (Merchant Category Code) generator
 *
 * @author Anton Kurako (GoodforGod)
 * @since 27.08.2022
 */
public final class MccGenerator implements Generator<CharSequence> {

    private static final Bundle BUNDLE = new MerchantBundle();
    private static final Pattern PATTERN = Pattern.compile("mcc|MerchantCategoryCode", CASE_INSENSITIVE);

    @Override
    public String get() {
        return BUNDLE.random(Localisation.ENGLISH);
    }

    @Override
    public Pattern pattern() {
        return PATTERN;
    }
}
