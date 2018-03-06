package io.dummymaker.generator.impl.collection.impl.basic;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.impl.number.LongGenerator;
import io.dummymaker.generator.impl.string.StringGenerator;

import java.util.HashMap;
import java.util.Map;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * "Default Description"
 *
 * @author GoodforGod
 * @since 24.02.2018
 */
public class MapLongStringGenerator implements IGenerator<Map<Long, String>> {

    private final IGenerator<Long> keyGenerator = new LongGenerator();
    private final IGenerator<String> valueGenerator = new StringGenerator();

    @Override
    public Map<Long, String> generate() {
        final Map<Long, String> objectsMap = new HashMap<>();
        final int amount = current().nextInt(1,10);

        for(int i = 0; i < amount; i++)
            objectsMap.put(keyGenerator.generate(), valueGenerator.generate());

        return objectsMap;
    }
}
