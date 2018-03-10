package io.dummymaker.generator.impl.collection.impl.basic;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.impl.number.IntegerGenerator;
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
public class MapIntStringGenerator implements IGenerator<Map<Integer, String>> {

    private final IGenerator<Integer> keyGenerator = new IntegerGenerator();
    private final IGenerator<String> valueGenerator = new StringGenerator();

    @Override
    public Map<Integer, String> generate() {
        final Map<Integer, String> objectsMap = new HashMap<>();
        final int amount = current().nextInt(1,10);

        for(int i = 0; i < amount; i++)
            objectsMap.put(keyGenerator.generate(), valueGenerator.generate());

        return objectsMap;
    }
}
