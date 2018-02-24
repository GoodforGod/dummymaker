package io.dummymaker.generator.impl.collection;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.impl.string.IdGenerator;
import io.dummymaker.generator.impl.string.JsonGenerator;

import java.util.HashMap;
import java.util.Map;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 21.02.2018
 */
public class MapStringObjectGenerator implements IGenerator<Map<String, Object>> {

    private final IGenerator<String> keyGenerator = new IdGenerator();
    private final IGenerator<String> valueGenerator = new JsonGenerator();

    @Override
    public Map<String, Object> generate() {
        final Map<String, Object> objects = new HashMap<>();
        final int amount = current().nextInt(10);

        return objects;
    }
}
