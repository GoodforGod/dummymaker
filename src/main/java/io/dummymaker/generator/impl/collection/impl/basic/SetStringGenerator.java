package io.dummymaker.generator.impl.collection.impl.basic;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.impl.string.StringGenerator;

import java.util.HashSet;
import java.util.Set;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Generates set of strings
 *
 * @see StringGenerator
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class SetStringGenerator implements IGenerator<Set<String>> {

    private final IGenerator<String> generator = new StringGenerator();

    @Override
    public Set<String> generate() {
        final Set<String> strings = new HashSet<>();
        final int amount = current().nextInt(1,10);

        for(int i = 0; i < amount; i++)
            strings.add(generator.generate());

        return strings;
    }
}
