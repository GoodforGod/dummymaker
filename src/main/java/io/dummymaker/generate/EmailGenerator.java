package io.dummymaker.generate;

import io.dummymaker.bundle.DomainExtensionPresetBundle;
import io.dummymaker.bundle.EmailServicesPresetBundle;
import io.dummymaker.bundle.NicknamesPresetBundle;

/**
 * Generates email as a string
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class EmailGenerator extends StringGenerator {

    @Override
    public String generate() {
        return new NicknamesPresetBundle().getRandom()
                + "@"
                + new EmailServicesPresetBundle().getRandom()
                + new DomainExtensionPresetBundle().getRandom();
    }
}
