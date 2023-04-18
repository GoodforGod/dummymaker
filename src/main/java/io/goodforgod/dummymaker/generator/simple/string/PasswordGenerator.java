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
 * @since 12.11.2022
 */
public final class PasswordGenerator implements Generator<CharSequence> {

    private static final Pattern PATTERN = Pattern.compile("pass(word)?", CASE_INSENSITIVE);
    private static final CharacterGenerator CHARACTER_GENERATOR = new CharacterGenerator();

    @Override
    public @NotNull String get() {
        final int length = RandomUtils.random(6, 20);
        final StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            final boolean isNumber = ThreadLocalRandom.current().nextBoolean();
            if (isNumber) {
                final int number = ThreadLocalRandom.current().nextInt(0, 10);
                builder.append(number);
            } else {
                final char character = CHARACTER_GENERATOR.get();
                builder.append(character);
            }
        }
        return builder.toString();
    }

    @Override
    public @NotNull Hints hints() {
        return Hints.builder()
                .withPattern(PATTERN)
                .build();
    }
}
