package io.dummymaker.generator.simple.impl.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.DomainExtensionPresetBundle;
import io.dummymaker.bundle.impl.EmailServicesPresetBundle;
import io.dummymaker.bundle.impl.NicknamesPresetBundle;
import io.dummymaker.generator.simple.IGenerator;

/**
 * Generates email as a string
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class EmailGenerator implements IGenerator<String> {

    private final IBundle<String> nickBundle = new NicknamesPresetBundle();
    private final IBundle<String> emailBundle = new EmailServicesPresetBundle();
    private final IBundle<String> domainBundle = new DomainExtensionPresetBundle();

    @Override
    public String generate() {
        return nickBundle.getRandom()
                + "@"
                + emailBundle.getRandom()
                + domainBundle.getRandom();
    }
}
