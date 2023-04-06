package io.goodforgod.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.bundle.Bundle;
import io.goodforgod.dummymaker.bundle.CurrencyBundle;
import io.goodforgod.dummymaker.generator.GenParameters;
import io.goodforgod.dummymaker.generator.Localisation;
import io.goodforgod.dummymaker.generator.ParameterizedGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 25.08.2022
 */
public final class CurrencyGenerator implements ParameterizedGenerator<CharSequence> {

    private static final Bundle BUNDLE = new CurrencyBundle();
    private static final Pattern PATTERN = Pattern.compile("currency|ticker|coin", CASE_INSENSITIVE);

    @Override
    public CharSequence get(@NotNull GenParameters parameters) {
        return parameters.namingCase().apply(getValue(parameters.localisation())).toString();
    }

    @Override
    public CharSequence get() {
        return getValue(Localisation.ENGLISH);
    }

    private static String getValue(@NotNull Localisation localisation) {
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
