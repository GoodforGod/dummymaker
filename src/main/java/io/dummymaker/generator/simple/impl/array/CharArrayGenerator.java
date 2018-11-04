package io.dummymaker.generator.simple.impl.array;

import io.dummymaker.generator.simple.IGenerator;
import io.dummymaker.generator.simple.impl.number.ByteGenerator;
import io.dummymaker.generator.simple.impl.number.CharGenerator;

/**
 * Generates array of chars
 *
 * @author GoodforGod
 * @since 04.11.2018
 */
public class CharArrayGenerator implements IGenerator<Character[]>{

    private final CharGenerator charGenerator = new CharGenerator();
    private final ByteGenerator generator = new ByteGenerator();

    @Override
    public Character[] generate() {
        final int size = generator.generate();
        final Character[] result = new Character[size];
        for(int i = 0; i < size; i++)
            result[i] = charGenerator.generate();

        return result;
    }
}
