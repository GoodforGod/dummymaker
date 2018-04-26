package io.dummymaker.generator.simple.impl;

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
        return ((char) ThreadLocalRandom.current().nextInt(34, 126));
    }
}
