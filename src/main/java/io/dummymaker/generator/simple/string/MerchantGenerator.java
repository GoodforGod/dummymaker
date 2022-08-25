package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.MerchantBundle;
import io.dummymaker.generator.IGenerator;
import java.util.regex.Pattern;

/**
 * @author Anton Kurako (GoodforGod)
 * @since 25.08.2022
 */
public class MerchantGenerator implements IGenerator<String> {

    private static final IBundle BUNDLE = new MerchantBundle();

    private final Pattern pattern = Pattern.compile("frequency", CASE_INSENSITIVE);

    @Override
    public String generate() {
        return BUNDLE.random();
    }

    @Override
    public Pattern pattern() {
        return pattern;
    }

    @Override
    public int order() {
        return -20;
    }
}
