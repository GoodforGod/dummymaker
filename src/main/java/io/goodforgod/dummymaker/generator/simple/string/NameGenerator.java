package io.goodforgod.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.bundle.Bundle;
import io.goodforgod.dummymaker.bundle.FemaleNameBundle;
import io.goodforgod.dummymaker.bundle.MaleNameBundle;
import io.goodforgod.dummymaker.generator.GenParameters;
import io.goodforgod.dummymaker.generator.Localisation;
import io.goodforgod.dummymaker.generator.ParameterizedGenerator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates names male and female as a string
 *
 * @author Anton Kurako (GoodforGod)
 * @since 26.05.2017
 */
public final class NameGenerator implements ParameterizedGenerator<CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("user|account|name|assignee|employee?|employer|worker",
            CASE_INSENSITIVE);

    private static final Bundle MALE_BUNDLE = new MaleNameBundle();
    private static final Bundle FEMALE_BUNDLE = new FemaleNameBundle();

    @Override
    public String get(@NotNull GenParameters parameters) {
        return parameters.namingCase().apply(get(parameters.localisation())).toString();
    }

    @Override
    public String get() {
        return get(Localisation.ENGLISH);
    }

    private static String get(@NotNull Localisation localisation) {
        return ThreadLocalRandom.current().nextBoolean()
                ? MALE_BUNDLE.random(localisation)
                : FEMALE_BUNDLE.random(localisation);
    }

    @Override
    public @NotNull Hints hints() {
        return Hints.builder()
                .withPattern(PATTERN)
                .withPriority(50)
                .build();
    }
}
