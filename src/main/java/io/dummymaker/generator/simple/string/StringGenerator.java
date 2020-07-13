package io.dummymaker.generator.simple.string;

import io.dummymaker.generator.IGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates random string like "aag2151tgdsfa9352tf"
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class StringGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("str", CASE_INSENSITIVE);

    @Override
    public @NotNull String generate() {
        final String s = UUID.randomUUID().toString() + UUID.randomUUID().toString();
        return s.replace("-", "");
    }

    @Override
    public @NotNull Pattern getPattern() {
        return pattern;
    }
}
