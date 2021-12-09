package io.dummymaker.generator.simple.string;


import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.CompanyBundle;
import io.dummymaker.generator.IGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;


/**
 * Generates company name as a string
 *
 * @author GoodforGod (Anton Kurako)
 * @since 07.06.2017
 */
public class CompanyGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("org(anization)?|company|corp(oration)?|fund|business|shop|store",
            CASE_INSENSITIVE);

    private static final IBundle bundle = new CompanyBundle();

    @Override
    public @NotNull String generate() {
        return bundle.random();
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }

    @Override
    public int order() {
        return -5;
    }
}
