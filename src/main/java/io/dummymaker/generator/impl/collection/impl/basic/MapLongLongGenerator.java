package io.dummymaker.generator.impl.collection.impl.basic;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.impl.number.LongGenerator;

import java.util.HashMap;
import java.util.Map;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 24.02.2018
 */
public class MapLongLongGenerator implements IGenerator<Map<Long, Long>> {

    private final IGenerator<Long> generator = new LongGenerator();

    @Override
    public Map<Long, Long> generate() {
        final Map<Long, Long> objectsMap = new HashMap<>();
        final int amount = current().nextInt(1,10);

        for(int i = 0; i < amount; i++)
            objectsMap.put(generator.generate(), generator.generate());

        return objectsMap;
    }
}
