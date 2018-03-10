package io.dummymaker.generator.impl.collection.impl.basic;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.impl.number.IntegerGenerator;

import java.util.HashMap;
import java.util.Map;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 24.02.2018
 */
public class MapIntIntGenerator implements IGenerator<Map<Integer, Integer>> {

    private final IGenerator<Integer> generator = new IntegerGenerator();

    @Override
    public Map<Integer, Integer> generate() {
        final Map<Integer, Integer> objectsMap = new HashMap<>();
        final int amount = current().nextInt(1,10);

        for(int i = 0; i < amount; i++)
            objectsMap.put(generator.generate(), generator.generate());

        return objectsMap;
    }
}
