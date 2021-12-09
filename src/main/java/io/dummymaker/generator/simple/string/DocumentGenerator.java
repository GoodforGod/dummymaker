package io.dummymaker.generator.simple.string;


import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.simple.number.CharacterGenerator;
import io.dummymaker.util.CollectionUtils;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;


/**
 * Generates document, password identifiers as a string
 *
 * @author GoodforGod
 * @since 26.05.2017
 */
public class DocumentGenerator implements IGenerator<String> {

    private final Pattern pattern = Pattern.compile("pass(word)?|project", CASE_INSENSITIVE);

    private final IGenerator<Character> prefixGenerator = new CharacterGenerator();

    @Override
    public @NotNull String generate() {
        final int id = CollectionUtils.random(10_000_000, 999_999_999);
        return ThreadLocalRandom.current().nextBoolean()
                ? prefixGenerator.generate() + String.valueOf(id)
                : String.valueOf(id);
    }

    @Override
    public @NotNull Pattern pattern() {
        return pattern;
    }
}
