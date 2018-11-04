package io.dummymaker.generator.simple.impl.array;

import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.number.CharGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates array of chars
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class CharArrayGenerator implements IGenerator<Character[]>{

    private final CharGenerator charGenerator = new CharGenerator();

    @Override
    public Character[] generate() {
        final int size = ThreadLocalRandom.current().nextInt(1, 20);
        final Character[] result = new Character[size];
        for(int i = 0; i < size; i++)
            result[i] = charGenerator.generate();

        return result;
    }
}
