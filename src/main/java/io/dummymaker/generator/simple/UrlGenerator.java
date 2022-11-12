package io.dummymaker.generator.simple;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.impl.DomainBundle;
import io.dummymaker.bundle.impl.LoginBundle;
import io.dummymaker.generator.Generator;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates URL as string
 *
 * @author Anton Kurako (GoodforGod)
 * @since 12.11.2022
 */
public final class UrlGenerator implements Generator<URL> {

    private static final Pattern PATTERN = Pattern.compile("site|website|url|server|link", CASE_INSENSITIVE);

    private static final Generator<URI> GENERATOR = new UriGenerator();

    private static final Bundle LOGIN_BUNDLE = new LoginBundle();
    private static final Bundle DOMAIN_BUNDLE = new DomainBundle();

    @Override
    public @NotNull URL get() {
        try {
            return URI.create("https://" + LOGIN_BUNDLE.random().replace(".", "") + DOMAIN_BUNDLE.random() + GENERATOR.get()).toURL();
        } catch (Exception e) {
            try {
                return URI.create("https://example.com").toURL();
            } catch (Exception ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
