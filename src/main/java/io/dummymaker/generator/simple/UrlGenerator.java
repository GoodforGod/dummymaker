package io.dummymaker.generator.simple;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.Bundle;
import io.dummymaker.bundle.DomainBundle;
import io.dummymaker.bundle.LoginBundle;
import io.dummymaker.generator.Localisation;
import io.dummymaker.generator.LocalizedGenerator;
import java.net.URI;
import java.net.URL;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates URL as string
 *
 * @author Anton Kurako (GoodforGod)
 * @since 12.11.2022
 */
public final class UrlGenerator implements LocalizedGenerator<URL> {

    private static final Pattern PATTERN = Pattern.compile("site|website|url|server|link", CASE_INSENSITIVE);

    private static final LocalizedGenerator<URI> URI_GENERATOR = new UriGenerator();

    private static final Bundle LOGIN_BUNDLE = new LoginBundle();
    private static final Bundle DOMAIN_BUNDLE = new DomainBundle();

    @Override
    public @NotNull URL get(@NotNull Localisation localisation) {
        try {
            final String urlAsString = "https://"
                    + LOGIN_BUNDLE.random(localisation).replace(".", "")
                    + DOMAIN_BUNDLE.random(localisation)
                    + URI_GENERATOR.get(localisation);

            return URI.create(urlAsString).toURL();
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
