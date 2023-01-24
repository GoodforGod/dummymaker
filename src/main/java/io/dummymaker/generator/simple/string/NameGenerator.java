package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.FemaleNameBundle;
import io.dummymaker.bundle.MaleNameBundle;
import io.dummymaker.generator.Localisation;
import io.dummymaker.generator.LocalizedGenerator;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates names male and female as a string
 *
 * @author Anton Kurako (GoodforGod)
 * @since 26.05.2017
 */
public final class NameGenerator implements LocalizedGenerator<String> {

    private static final Pattern PATTERN = Pattern.compile("user|account|name|assignee|employe|worker", CASE_INSENSITIVE);

    private static final Bundle MALE_BUNDLE = new MaleNameBundle();
    private static final Bundle FEMALE_BUNDLE = new FemaleNameBundle();

    @Override
    public @NotNull String get(@NotNull Localisation localisation) {
        return ThreadLocalRandom.current().nextBoolean()
                ? MALE_BUNDLE.random(localisation)
                : FEMALE_BUNDLE.random(localisation);
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }

    @Override
    public int order() {
        return -60;
    }
}
