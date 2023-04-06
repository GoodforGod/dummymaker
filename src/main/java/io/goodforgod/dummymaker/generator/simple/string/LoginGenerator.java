package io.goodforgod.dummymaker.generator.simple.string;

import static java.util.concurrent.ThreadLocalRandom.current;
import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.bundle.Bundle;
import io.goodforgod.dummymaker.bundle.LoginBundle;
import io.goodforgod.dummymaker.generator.GenParameters;
import io.goodforgod.dummymaker.generator.Localisation;
import io.goodforgod.dummymaker.generator.ParameterizedGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates nicknames as a string
 *
 * @author Anton Kurako (GoodforGod) (Anton Kurako)
 * @since 05.06.2017
 */
public final class LoginGenerator implements ParameterizedGenerator<CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("nick(name)?|login", CASE_INSENSITIVE);
    private static final Bundle BUNDLE = new LoginBundle();

    @Override
    public String get(@NotNull GenParameters parameters) {
        return parameters.namingCase().apply(get(parameters.localisation())).toString();
    }

    @Override
    public String get() {
        return get(Localisation.ENGLISH);
    }

    private static String get(@NotNull Localisation localisation) {
        final String first = BUNDLE.random(localisation);
        final boolean tuple = current().nextBoolean();
        if (!tuple)
            return first;

        final String second = BUNDLE.random(localisation);
        final boolean revert = current().nextBoolean();

        final String separator = current().nextBoolean()
                ? "_"
                : "";

        return (revert)
                ? second + separator + first
                : first + separator + second;
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
