package io.dummymaker.generator.simple.string;

import io.dummymaker.generator.IGenerator;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates gender as string
 *
 * @author Anton Kurako (GoodforGod)
 * @since 4.5.2020
 */
public class GenderGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("sex|gender", CASE_INSENSITIVE);

    @Nullable
    @Override
    public String generate() {
        return ThreadLocalRandom.current().nextBoolean()
                ? "male"
                : "female";
    }

    @Override
    public @Nullable Pattern getPattern() {
        return pattern;
    }
}
