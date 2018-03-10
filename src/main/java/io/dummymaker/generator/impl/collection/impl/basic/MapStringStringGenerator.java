package io.dummymaker.generator.impl.collection.impl.basic;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.impl.string.JsonGenerator;
import io.dummymaker.generator.impl.string.StringGenerator;

import java.util.HashMap;
import java.util.Map;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class MapStringStringGenerator implements IGenerator<Map<Object, Object>> {

    private final IGenerator<String> keyGenerator = new StringGenerator();
    private final IGenerator<String> valueGenerator = new JsonGenerator();

    @Override
    public Map<Object, Object> generate() {
        final Map<Object, Object> objectsMap = new HashMap<>();
        final int amount = current().nextInt(1,10);

        for(int i = 0; i < amount; i++)
            objectsMap.put(keyGenerator.generate(), valueGenerator.generate());

        return objectsMap;
    }
}
