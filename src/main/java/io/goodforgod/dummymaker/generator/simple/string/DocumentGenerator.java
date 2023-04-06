package io.goodforgod.dummymaker.generator.simple.string;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.goodforgod.dummymaker.generator.Generator;
import io.goodforgod.dummymaker.generator.simple.number.CharacterGenerator;
import io.goodforgod.dummymaker.util.RandomUtils;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

/**
 * Generates document, password identifiers as a string
 *
 * @author Anton Kurako (GoodforGod)
 * @since 26.05.2017
 */
public final class DocumentGenerator implements Generator<CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("pass(word)?|project", CASE_INSENSITIVE);

    private static final Generator<Character> CHARACTER_GENERATOR = new CharacterGenerator();

    @Override
    public @NotNull String get() {
        final int id = RandomUtils.random(10_000_000, 999_999_999);
        return ThreadLocalRandom.current().nextBoolean()
                ? CHARACTER_GENERATOR.get() + String.valueOf(id)
                : String.valueOf(id);
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
