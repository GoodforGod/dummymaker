package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.CurrencyBundle;
import io.dummymaker.generator.Localisation;
import io.dummymaker.generator.LocalizedGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 25.08.2022
 */
public final class CurrencyGenerator implements LocalizedGenerator<String> {

    private static final Bundle BUNDLE = new CurrencyBundle();
    private static final Pattern PATTERN = Pattern.compile("currency|ticker|coin", CASE_INSENSITIVE);

    @Override
    public String get(@NotNull Localisation localisation) {
        return BUNDLE.random(localisation);
    }

    @Override
    public Pattern pattern() {
        return PATTERN;
    }

    @Override
    public int order() {
        return -30;
    }
}
