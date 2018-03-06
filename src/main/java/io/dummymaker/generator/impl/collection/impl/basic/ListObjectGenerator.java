package io.dummymaker.generator.impl.collection.impl.basic;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.impl.string.IdGenerator;

import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Generates list of objects based on Ids
 *
 * @see IdGenerator
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class ListObjectGenerator implements IGenerator<List<Object>> {

    private final IGenerator<String> generator = new IdGenerator();

    @Override
    public List<Object> generate() {
        final List<Object> objects = new ArrayList<>();
        final int amount = current().nextInt(1,10);

        for(int i = 0; i < amount; i++)
            objects.add(generator.generate());

        return objects;
    }
}
