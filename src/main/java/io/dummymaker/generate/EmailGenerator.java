package io.dummymaker.generate;

import io.dummymaker.bundle.DomainExtensionPresetBundle;
import io.dummymaker.bundle.EmailServicesPresetBundle;
import io.dummymaker.bundle.NicknamesPresentBundle;

/**
 * Generates email as a string
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class EmailGenerator extends StringGenerator {

    @Override
    public String generate() {
        return new NicknamesPresentBundle().getRandom()
                + "@"
                + new EmailServicesPresetBundle().getRandom()
                + new DomainExtensionPresetBundle().getRandom();
    }
}
