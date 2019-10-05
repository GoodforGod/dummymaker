package io.dummymaker.generator.simple.number;

import io.dummymaker.generator.IGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates random UTF-8 character in range of 0-255
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class CharGenerator implements IGenerator<Character> {

    @Override
    public Character generate() {
        return ((char) ThreadLocalRandom.current().nextInt(0, 255));
    }
}
