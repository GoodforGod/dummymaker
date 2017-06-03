package io.model.dummymaker.generate;

import io.model.dummymaker.bundle.DomainExtensionPresetBundle;
import io.model.dummymaker.bundle.EmailServicesPresetBundle;
import io.model.dummymaker.bundle.NicknamesPresentBundle;

/**
 * Default Comment
 *
 * @author @GoodforGod
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
