package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.DomainBundle;
import io.dummymaker.bundle.EmailServicesBundle;
import io.dummymaker.bundle.LoginBundle;
import io.dummymaker.generator.Generator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates email as a string
 *
 * @author Anton Kurako (GoodforGod)
 * @since 26.05.2017
 */
public final class EmailGenerator implements Generator<String> {

    private static final Bundle LOGIN_BUNDLE = new LoginBundle();
    private static final Bundle EMAIL_BUNDLE = new EmailServicesBundle();
    private static final Bundle DOMAIN_BUNDLE = new DomainBundle();
    private static final Pattern PATTERN = Pattern.compile("e?mail", CASE_INSENSITIVE);

    @Override
    public @NotNull String get() {
        return LOGIN_BUNDLE.random()
                + "@"
                + EMAIL_BUNDLE.random()
                + DOMAIN_BUNDLE.random();
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
