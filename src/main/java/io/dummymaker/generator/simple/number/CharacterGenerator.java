package io.dummymaker.generator.simple.number;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

import io.dummymaker.generator.Generator;
import io.dummymaker.util.CollectionUtils;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import io.dummymaker.util.RandomUtils;
import org.jetbrains.annotations.NotNull;

/**
 * Generates random UTF-8 character in range of (97-122)
 *
 * @author Anton Kurako (GoodforGod)
 * @since 26.04.2018
 */
public final class CharacterGenerator implements Generator<Character> {

    private static final Pattern PATTERN = Pattern.compile("letter|character", CASE_INSENSITIVE);

    @Override
    public @NotNull Character get() {
        boolean b = ThreadLocalRandom.current().nextBoolean();
        char c = (char) RandomUtils.random(97, 122);
        return (b)
                ? Character.toUpperCase(c)
                : c;
    }

    @Override
    public @NotNull Pattern pattern() {
        return PATTERN;
    }
}
