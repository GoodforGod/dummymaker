package io.goodforgod.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.bundle.Bundle;
import io.goodforgod.dummymaker.bundle.DomainBundle;
import io.goodforgod.dummymaker.bundle.EmailServicesBundle;
import io.goodforgod.dummymaker.bundle.LoginBundle;
import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.Localisation;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates email as a string
 *
 * @author Anton Kurako (GoodforGod)
 * @since 26.05.2017
 */
public final class EmailGenerator implements Generator<CharSequence> {

    private static final Bundle LOGIN_BUNDLE = new LoginBundle();
    private static final Bundle EMAIL_BUNDLE = new EmailServicesBundle();
    private static final Bundle DOMAIN_BUNDLE = new DomainBundle();
    private static final Pattern PATTERN = Pattern.compile("e?mail", CASE_INSENSITIVE);

    @Override
    public @NotNull String get() {
        return LOGIN_BUNDLE.random(Localisation.ENGLISH)
                + "@"
                + EMAIL_BUNDLE.random(Localisation.ENGLISH)
                + DOMAIN_BUNDLE.random(Localisation.ENGLISH);
    }

    @Override
    public @NotNull Hints hints() {
        return Hints.builder()
                .withPattern(PATTERN)
                .build();
    }
}
