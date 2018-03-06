package io.dummymaker.generator.impl.collection.impl.basic;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.impl.string.IdGenerator;

import java.util.HashSet;
import java.util.Set;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Generates set of objects based on Ids
 *
 * @see IdGenerator
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class SetObjectGenerator implements IGenerator<Set<Object>>{

    private final IGenerator<String> generator = new IdGenerator();

    @Override
    public Set<Object> generate() {
        final Set<Object> strings = new HashSet<>();
        final int amount = current().nextInt(1,10);

        for(int i = 0; i < amount; i++)
            strings.add(generator.generate());

        return strings;
    }
}
