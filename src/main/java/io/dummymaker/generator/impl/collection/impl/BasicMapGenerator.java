package io.dummymaker.generator.impl.collection.impl;

import io.dummymaker.generator.IGenerator;
import io.dummymaker.generator.impl.collection.IMapGenerator;
import io.dummymaker.generator.impl.string.IdBigGenerator;
import io.dummymaker.generator.impl.string.IdGenerator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static io.dummymaker.util.BasicCastUtils.*;

/**
 * Generate map of elements with key,value of object type
 *
 * @see io.dummymaker.generator.impl.collection.IMapGenerator
 *
 * @author GoodforGod
 * @since 06.03.2018
 */
abstract class BasicMapGenerator<K, V> implements IMapGenerator<K, V> {

    private final IGenerator keyGenerator;
    private final IGenerator valueGenerator;

    BasicMapGenerator() {
        this.keyGenerator = new IdGenerator();
        this.valueGenerator = new IdBigGenerator();
    }

    BasicMapGenerator(final IGenerator keyGenerator,
                      final IGenerator valueGenerator) {
        this.keyGenerator = keyGenerator;
        this.valueGenerator = valueGenerator;
    }

    @Override
    public Map<K, V> generate() {
        return generate(keyGenerator, valueGenerator,
                Object.class, Object.class,
                0, 10);
    }

    @Override
    public Map<K, V> generate(final IGenerator keyGenerator,
                              final IGenerator valueGenerator,
                              final Class<?> keyType,
                              final Class<?> valueType,
                              final int min,
                              final int max) {

        final Map map = new HashMap<>();
        final int amount = generateRandomAmount(min, max);

        for (int i = 0; i < amount; i++) {
            final Object key = generateObject(keyGenerator, keyType);
            final Object value = generateObject(valueGenerator, valueType);

            if (key.equals(EMPTY))
                return Collections.emptyMap();

            map.put(key, value);
        }

        return map;
    }
}
