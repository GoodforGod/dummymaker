package io.dummymaker.generator.simple.impl.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.DomainExtensionBundle;
import io.dummymaker.bundle.impl.EmailServicesBundle;
import io.dummymaker.bundle.impl.NicknamesBundle;
import io.dummymaker.generator.simple.IGenerator;

/**
 * Generates email as a string
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class EmailGenerator implements IGenerator<String> {

    private final IBundle<String> nickBundle = new NicknamesBundle();
    private final IBundle<String> emailBundle = new EmailServicesBundle();
    private final IBundle<String> domainBundle = new DomainExtensionBundle();

    @Override
    public String generate() {
        return nickBundle.getRandom()
                + "@"
                + emailBundle.getRandom()
                + domainBundle.getRandom();
    }
}
