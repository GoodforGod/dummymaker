package io.dummymaker.generator.simple.string;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.simple.number.CharacterGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;
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

    private final IGenerator<Character> prefixGenerator = new CharacterGenerator();

    @Override
    public @NotNull String generate() {
        final int id = ThreadLocalRandom.current().nextInt(10_000_000, 999_999_999);
        return (ThreadLocalRandom.current().nextBoolean())
                ? prefixGenerator.generate() + String.valueOf(id)
                : String.valueOf(id);
    }

    @Override
    public @NotNull Pattern getPattern() {
        return pattern;
    }
}
