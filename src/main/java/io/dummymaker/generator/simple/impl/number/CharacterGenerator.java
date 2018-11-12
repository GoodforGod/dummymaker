package io.dummymaker.generator.simple.impl.number;

import io.dummymaker.generator.simple.IGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates random UTF-8 character in range of 34-126
 *
 * @author GoodforGod
 * @since 26.04.2018
 */
public class CharacterGenerator implements IGenerator<Character> {

    @Override
    public Character generate() {
        boolean b = ThreadLocalRandom.current().nextBoolean();
        char c = (char) ThreadLocalRandom.current().nextInt(97, 122);
        return (b) ? Character.toUpperCase(c) : c;
    }
}
