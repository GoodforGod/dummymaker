package io.dummymaker.generator.simple.string;


import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.JobBundle;
import io.dummymaker.generator.IGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.Nullable;


/**
 * Generates roles
 *
 * @author GoodforGod
 * @since 13.07.2020
 */
public class RoleGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("role", CASE_INSENSITIVE);

    private static final IBundle bundle = new JobBundle();

    @Nullable
    @Override
    public String generate() {
        return bundle.random().toLowerCase().replace(' ', '-');
    }

    @Override
    public @Nullable Pattern pattern() {
        return pattern;
    }
}
