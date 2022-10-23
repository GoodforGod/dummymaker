package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.MerchantBundle;
import io.dummymaker.generator.IGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.Nullable;

/**
 * Bank MCC (Merchant Category Code) generator
 *
 * @author Anton Kurako (GoodforGod)
 * @since 27.08.2022
 */
public class MccGenerator implements IGenerator<String> {

    private static final IBundle BUNDLE = new MerchantBundle();

    private final Pattern pattern = Pattern.compile("mcc|MerchantCategoryCode", CASE_INSENSITIVE);

    @Override
    public @Nullable String generate() {
        return BUNDLE.random();
    }

    @Override
    public @Nullable Pattern pattern() {
        return pattern;
    }
}
