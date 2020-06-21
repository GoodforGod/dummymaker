package io.dummymaker.generator.simple.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.DomainExtensionBundle;
import io.dummymaker.bundle.impl.NicknamesBundle;
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

    private final Pattern pattern = Pattern.compile("url|server", CASE_INSENSITIVE);

    private final IBundle<String> domains = new NicknamesBundle();
    private final IBundle<String> zones = new DomainExtensionBundle();

    @Override
    public @NotNull String generate() {
        return "https://" + domains.getRandom().replace(".", "") + zones.getRandom() + super.generate();
    }

    @Override
    public @NotNull Pattern getPattern() {
        return pattern;
    }
}
