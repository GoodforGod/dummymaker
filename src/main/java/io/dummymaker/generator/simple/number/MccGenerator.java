package io.dummymaker.generator.simple.number;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.impl.MccBundle;
import io.dummymaker.generator.Generator;
import java.util.regex.Pattern;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 22.10.2022
 */
public final class MccGenerator implements Generator<Integer> {

    private static final Bundle BUNDLE = new MccBundle();
    private static final Pattern PATTERN = Pattern.compile("mcc", CASE_INSENSITIVE);

    @Override
    public Integer get() {
        return Integer.valueOf(BUNDLE.random());
    }

    @Override
    public Pattern pattern() {
        return PATTERN;
    }

    @Override
    public int order() {
        return -40;
    }
}