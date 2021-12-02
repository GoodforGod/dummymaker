package io.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.bundle.IBundle;
import io.dummymaker.bundle.impl.SurnameBundle;
import io.dummymaker.generator.IGenerator;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Surname generator
 *
 * @author GoodforGod
 * @since 06.03.2019
 */
public class SurnameGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("surname", CASE_INSENSITIVE);

    private final IBundle bundle = new SurnameBundle();

    @Override
    public @NotNull String generate() {
        return bundle.random();
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }
}
