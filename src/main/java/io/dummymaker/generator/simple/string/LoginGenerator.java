package io.dummymaker.generator.simple.string;

import static java.util.concurrent.ThreadLocalRandom.current;
import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.impl.LoginBundle;
import io.dummymaker.generator.Generator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates nicknames as a string
 *
 * @author Anton Kurako (GoodforGod) (Anton Kurako)
 * @since 05.06.2017
 */
public final class LoginGenerator implements Generator<String> {

    private static final Pattern PATTERN = Pattern.compile("nick(name)?|login", CASE_INSENSITIVE);
    private static final Bundle BUNDLE = new LoginBundle();

    @Override
    public @NotNull String get() {
        final String first = BUNDLE.random();
        final boolean tuple = current().nextBoolean();
        if (!tuple)
            return first;

        final String second = BUNDLE.random();
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
