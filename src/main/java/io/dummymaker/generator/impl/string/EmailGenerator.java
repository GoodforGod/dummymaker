package io.dummymaker.generator.impl.string;

import io.dummymaker.bundle.impl.DomainExtensionPresetBundle;
import io.dummymaker.bundle.impl.EmailServicesPresetBundle;
import io.dummymaker.bundle.impl.NicknamesPresetBundle;

/**
 * Generates email as a string
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class EmailGenerator extends BigIdGenerator {

    @Override
    public String generate() {
        return new NicknamesPresetBundle().getRandom()
                + "@"
                + new EmailServicesPresetBundle().getRandom()
                + new DomainExtensionPresetBundle().getRandom();
    }
}
