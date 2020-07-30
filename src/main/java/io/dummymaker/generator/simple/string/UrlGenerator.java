package io.dummymaker.generator.simple.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.DomainBundle;
import io.dummymaker.bundle.impl.LoginBundle;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates URL as string
 *
 * @author Anton Kurako (GoodforGod)
 * @since 4.5.2020
 */
public class UrlGenerator extends UriGenerator {

    private final Pattern pattern = Pattern.compile("url|server|link", CASE_INSENSITIVE);

    private final IBundle domains = new LoginBundle();
    private final IBundle zones = new DomainBundle();

    @Override
    public @NotNull String generate() {
        return "https://" + domains.random().replace(".", "") + zones.random() + super.generate();
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }
}
