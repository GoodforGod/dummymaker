package io.dummymaker.generator.simple.string;

import io.dummymaker.generator.IGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates document, password as a string identifier
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class DocGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("pass(word)?|doc(ument)?|org", CASE_INSENSITIVE);

    @Override
    public @NotNull String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public @NotNull Pattern getPattern() {
        return pattern;
    }
}
