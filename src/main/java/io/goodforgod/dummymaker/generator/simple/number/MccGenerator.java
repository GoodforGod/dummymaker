package io.goodforgod.dummymaker.generator.simple.number;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.bundle.Bundle;
import io.goodforgod.dummymaker.bundle.MccBundle;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.Localisation;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 22.10.2022
 */
public final class MccGenerator implements Generator<Integer> {

    private static final Bundle BUNDLE = new MccBundle();
    private static final Pattern PATTERN = Pattern.compile("mcc|MerchantCategoryCode", CASE_INSENSITIVE);

    @Override
    public Integer get() {
        return Integer.valueOf(BUNDLE.random(Localisation.ENGLISH));
    }

    @Override
    public @NotNull Hints hints() {
        return Hints.builder()
                .withPattern(PATTERN)
                .withPriority(40)
                .build();
    }
}
