package io.dummymaker.generator.simple.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.LoginBundle;
import io.dummymaker.generator.IGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static java.util.concurrent.ThreadLocalRandom.current;
import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates nicknames as a string
 *
 * @author GoodforGod (Anton Kurako)
 * @since 05.06.2017
 */
public class LoginGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("nick(name)?|login", CASE_INSENSITIVE);

    private final IBundle bundle = new LoginBundle();

    @Override
    public @NotNull String generate() {
        final String first = bundle.random();
        final boolean tuple = current().nextBoolean();
        if (!tuple)
            return first;

        final String second = bundle.random();
        final boolean revert = current().nextBoolean();

        final String separator = current().nextBoolean() ? "_" : "";
        return (revert)
                ? second + separator + first
                : first + separator + second;
    }

    @Override
    public @NotNull Pattern getPattern() {
        return pattern;
    }
}
