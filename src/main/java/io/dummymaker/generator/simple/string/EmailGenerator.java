package io.dummymaker.generator.simple.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.DomainExtensionBundle;
import io.dummymaker.bundle.impl.EmailServicesBundle;
import io.dummymaker.bundle.impl.NicknamesBundle;
import io.dummymaker.generator.IGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates email as a string
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class EmailGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("e?mail", CASE_INSENSITIVE);

    private final IBundle<String> nickBundle = new NicknamesBundle();
    private final IBundle<String> emailBundle = new EmailServicesBundle();
    private final IBundle<String> domainBundle = new DomainExtensionBundle();

    @Override
    public @NotNull String generate() {
        return nickBundle.getRandom()
                + "@"
                + emailBundle.getRandom()
                + domainBundle.getRandom();
    }

    @Override
    public @NotNull Pattern getPattern() {
        return pattern;
    }
}
