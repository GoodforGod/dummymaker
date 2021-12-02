package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.DomainBundle;
import io.dummymaker.bundle.impl.EmailServicesBundle;
import io.dummymaker.bundle.impl.LoginBundle;
import io.dummymaker.generator.IGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates email as a string
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class EmailGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("e?mail", CASE_INSENSITIVE);

    private final IBundle nickBundle = new LoginBundle();
    private final IBundle emailBundle = new EmailServicesBundle();
    private final IBundle domainBundle = new DomainBundle();

    @Override
    public @NotNull String generate() {
        return nickBundle.random()
                + "@"
                + emailBundle.random()
                + domainBundle.random();
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }
}
