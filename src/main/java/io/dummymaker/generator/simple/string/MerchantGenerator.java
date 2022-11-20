package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.impl.MerchantBundle;
import io.dummymaker.generator.Generator;
import java.util.regex.Pattern;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 25.08.2022
 */
public final class MerchantGenerator implements Generator<String> {

    private static final Bundle BUNDLE = new MerchantBundle();
    private static final Pattern PATTERN = Pattern.compile("merchant|broker|dealer|seller|retailer|trader|shipper|vendor",
            CASE_INSENSITIVE);

    @Override
    public String get() {
        return BUNDLE.random();
    }

    @Override
    public Pattern pattern() {
        return PATTERN;
    }

    @Override
    public int order() {
        return -20;
    }
}
