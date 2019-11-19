package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.IGenerator;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/**
 * Generates random UTF-8 character in range of (97-122)
 *
 * @author GoodforGod
 * @since 26.04.2018
 */
public class CharacterGenerator implements IGenerator<Character> {

    private final Pattern pattern = Pattern.compile("letter|character", CASE_INSENSITIVE);

    @Override
    public Character generate() {
        boolean b = ThreadLocalRandom.current().nextBoolean();
        char c = (char) ThreadLocalRandom.current().nextInt(97, 122);
        return (b) ? Character.toUpperCase(c) : c;
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }
}
