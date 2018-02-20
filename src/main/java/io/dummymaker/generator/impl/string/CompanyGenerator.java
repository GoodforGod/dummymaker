package io.dummymaker.generator.impl.string;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.CompanyPresetBundle;
import io.dummymaker.generator.IGenerator;

/**
 * Generates company name as a string
 *
 * @author GoodforGod (Anton Kurako)
 * @since 07.06.2017
 */
public class CompanyGenerator implements IGenerator<String> {

    private final IBundle<String> bundle = new CompanyPresetBundle();

    @Override
    public String generate() {
        return bundle.getRandom();
    }
}
